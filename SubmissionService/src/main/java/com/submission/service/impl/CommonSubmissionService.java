package com.submission.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.common.view.QuestionView;
import com.quiz.common.view.SubmissionAnswerView;
import com.quiz.common.view.SubmissionView;
import com.submission.model.Submission;
import com.submission.model.SubmissionAnswer;
import com.submission.repository.SubmissionAnswerRepo;
import com.submission.repository.SubmissionRepo;

@Service
public class CommonSubmissionService {
	@Autowired
	private SubmissionAnswerRepo answerRepo;
	
	@Autowired
	private SubmissionRepo submissionRepo;

	public void saveAll(List<SubmissionAnswerView> answers) throws Exception {
		for(SubmissionAnswerView ans : answers) {
			validateAnswerView(ans, false);
			answerRepo.save(toSubmissionAnswer(ans));
		}
		
	}

	public void validateSubmissionStatus(Long submissionId) throws Exception {
			Submission sub = submissionRepo.findById(submissionId).orElseThrow(()-> new Exception("No quiz started for this user"));
			if(!sub.getStatus().equalsIgnoreCase("START")) {
				throw new Exception("Quiz already submitted by the user");
			}
	}
	
	public void validateAnswerView(SubmissionAnswerView view, boolean validateSubmissionStatus) throws Exception {
		if(view == null) {
			throw new IllegalArgumentException("No data to submit");
		}
		if(view.getQuestion()==null ||view.getQuestion().getId()==null) {
			throw new IllegalArgumentException("Question cannot be null");
		}
		if(validateSubmissionStatus) {
			validateSubmissionStatus(view.getSubmission().getId());;
		}
	}
	
	public SubmissionAnswer toSubmissionAnswer(SubmissionAnswerView view) {
        if (view == null) {
            return null;
        }
        return SubmissionAnswer.builder()
                .id(view.getId())
                .questionId(view.getQuestion().getId())
                .givenAnswer(view.getGivenAnswer())
                .correct(view.getCorrect())
                .points(view.getPointsAwarded())
                .build();
    }

	public List<SubmissionAnswerView> getAllSubmissionAnswers(Long submissionId) {
		List<SubmissionAnswer> answers=answerRepo.findBySubmissionId(submissionId);
		return toAnswerViewList(answers);
	}
	
	public Optional<SubmissionAnswerView> toSubmissionAnswerView(SubmissionAnswer entity) {
        return Optional.ofNullable(entity)
                .map(answer -> SubmissionAnswerView.builder()
                        .id(answer.getId())
                        .givenAnswer(answer.getGivenAnswer())
                        .correct(answer.getCorrect())
                        .pointsAwarded(answer.getPoints())
                        .question(QuestionView.builder().id(answer.getQuestionId()).build())
                        .submission(SubmissionView.builder().id(answer.getSubmissionId()).build())
                        .build()
                );
    }
    
	public List<SubmissionAnswerView> toAnswerViewList(List<SubmissionAnswer> subAns) {
		return Objects.requireNonNullElse(subAns, new ArrayList<SubmissionAnswer>())
				.stream().map(this::toSubmissionAnswerView).map(Optional::get).toList();
	}
}
