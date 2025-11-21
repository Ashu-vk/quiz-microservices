package com.submission.service.impl;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.common.view.QuestionView;
import com.quiz.common.view.QuizView;
import com.quiz.common.view.SubmissionAnswerView;
import com.quiz.common.view.SubmissionView;
import com.quiz.common.view.UserView;
import com.submission.model.Submission;
import com.submission.repository.SubmissionRepo;
import com.submission.service.SubmissionService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SubmissionServiceImpl implements SubmissionService {

	@Autowired
	private SubmissionRepo repository;
	
	@Autowired
	private CommonSubmissionService commonService;
	
	@Autowired
	private UserFeignClientImpl userService;
	
	@Autowired
	private QuizFeignServiceImpl quizService;
	
	@Autowired
	private QuestionFiegnServiceImpl questionFiegnService;
	
	@Override
	public List<SubmissionView> getSubmissions() {
		return toViewList(repository.findAll());	
	}
	@Override
	public List<SubmissionView> getSubmissionsByQuiz(Long quizId) {
		return toViewList(repository.findByQuizId(quizId));	
	}
	@Override
	public List<SubmissionView> getSubmissionsByUser(Long userId) {
		return toViewList(repository.findByUserId(userId));	
	}
	@Override
	public SubmissionView getSubmissionById(Long id)  {
		Submission sub = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("No submission found for id"+ id));
		SubmissionView view =toView(sub).get();
		if(view!=null) {
			view.setUser(userService.getUserById(sub.getUserId()));
			view.setQuiz(quizService.getQuizById(sub.getQuizId()));;
		}
		return view;
	}
	@Override
	public Optional<SubmissionView> saveOrUpdateSubmission(SubmissionView view) {
		if(view==null) {
			throw new IllegalArgumentException("Submission can not be null");
		}
		return toView(repository.save(toEntity(view)));
	}
	
	private List<SubmissionView> toViewList(List<Submission> users) {
		return Objects.requireNonNullElse(users, new ArrayList<Submission>())
				.stream().map(this::toView).map(Optional::get).toList();
	}
	
	
    private Submission toEntity(SubmissionView view) {
        if (view == null) {
            return null;
        }
        return Submission.builder()
                .id(view.getId())
                .quizId(view.getQuiz()==null? null:view.getQuiz().getId())
                .userId(view.getUser()==null? null:view.getUser().getId())
                .startTime(view.getStartTime())
                .endTime(view.getEndTime())
                .score(view.getScore())
                .totalPoints(view.getTotalPoints())
                .status(view.getStatus())
                .build();
    }
    
	
    private Optional<SubmissionView> toView(Submission entity) {
        return Optional.ofNullable(entity)
                .map(answer -> SubmissionView.builder()
                		.id(answer.getId())
                        .quiz(QuizView.builder().id(answer.getQuizId()).build())
                        .user(UserView.builder().id(answer.getUserId()).build())
                        .startTime(answer.getStartTime())
                        .endTime(answer.getEndTime())
                        .score(answer.getScore())
                        .totalPoints(answer.getTotalPoints())
                        .status(answer.getStatus())
                        .build()
                );
    }
	@Override
	public SubmissionView startQuiz(QuizView quiz, Long userId) {
		System.err.println("userid from submission" + userId);
		SubmissionView view =SubmissionView.builder()
		.quiz(quiz)
		.startTime(LocalDateTime.now())
		.status("STARTED")
		.totalPoints(quiz.getQuestions().stream().map(QuestionView::getPoints).reduce(0, Integer::sum))
		
		.user(userService.getUserById(userId))
		.build();
		return saveOrUpdateSubmission(view).get();
	}
	@Override
	public SubmissionView submitQuiz(QuizView quiz, Long userId) throws Exception {
		Submission submission = repository.findByQuizIdAndUserId(quiz.getId(), userId)
				.orElseThrow(() -> new Exception("User does not have submitted for quiz"+ quiz.getId()));
		if(!submission.getStatus().equals("STARTED")) {
			throw new IllegalStateException(String.format("Submission %s status must be in STARTED", submission.getId()));
		}
		submission.setEndTime(LocalDateTime.now());
		submission.setStatus("SUBMITTED");
		SubmissionView subView = toView(submission).get();
		subView.setAnswers(commonService.getAllSubmissionAnswers(submission.getId()));
		subView = questionFiegnService.sendSubmissionstoVerify(subView).getBody();
		subView.setScore(subView.getAnswers().stream().filter(a->a.getCorrect()).map(SubmissionAnswerView::getPointsAwarded).reduce(Integer::sum).orElse(0));
		commonService.saveAll(subView.getAnswers());
		return saveOrUpdateSubmission(subView).get();
	}
}
