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
import com.quiz.common.view.SubmissionView;
import com.submission.fign.services.QuizFeignService;
import com.submission.fign.services.UserFeignClient;
import com.submission.model.Submission;
import com.submission.repository.SubmissionRepo;
import com.submission.service.SubmissionService;

@Service
public class SubmissionServiceImpl implements SubmissionService {

	@Autowired
	private SubmissionRepo repository;
	
	@Autowired
	private UserFeignClient userService;
	@Autowired
	private QuizFeignService quizFiegnService;
	
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
	public SubmissionView getSubmissionById(Long id) throws Exception {
		Submission sub = repository.findById(id).get();
		SubmissionView view =toView(sub).orElseThrow(()-> new Exception("No submission found for id"+ id));
		view.setUser(userService.getUserById(sub.getUserId()));
		view.setQuiz(quizFiegnService.getQuizById(sub.getQuizId()));;
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
                .quizId(view.getQuiz().getId())
                .userId(view.getUser().getId())
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
//                        .quizId(answer.getQuizId())
//                        .userId(answer.getUserId())
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
		SubmissionView view =SubmissionView.builder()
		.quiz(quiz)
		.startTime(LocalDateTime.now())
		.status("STARTED")
		.totalPoints(quiz.getQuestions().stream().map(QuestionView::getPoints).reduce(0, Integer::sum))
		.user(userService.getUserById(userId))
		.build();
		return saveOrUpdateSubmission(view).get();
	}
}
