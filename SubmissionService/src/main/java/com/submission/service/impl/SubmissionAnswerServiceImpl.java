package com.submission.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.submission.model.SubmissionAnswer;
import com.submission.repository.SubmissionAnswerRepo;
import com.submission.service.SubmissionAnswerService;
import com.submission.view.SubmissionAnswerView;

@Service
public class SubmissionAnswerServiceImpl implements SubmissionAnswerService{
	
	@Autowired
	private SubmissionAnswerRepo repository;
	
	@Override
	public Optional<SubmissionAnswerView> getById(Long sId){
		if (sId == null) {
			return Optional.empty();
		}
		return toView(repository.findById(sId).orElse(null));
	}
	
	@Override
	public List<SubmissionAnswerView> getAll(){
		return toViewList(repository.findAll());
	}
	@Override
	public List<SubmissionAnswerView> getAllByQuizAndUser(Long quizId, Long userId){
		return toViewList(repository.findAllByQuizAndUser(quizId, userId));
	}
	
	@Override
	public Optional<SubmissionAnswerView> saveOrUpdate(Long quizId, Long userId, SubmissionAnswerView view) {
		if(view == null) {
			throw new IllegalArgumentException("User cannot be null");
		}
		view.setSubmissionId(userId);
		view.setQuizId(quizId);
		SubmissionAnswer subAnswer = toEntity(view);
		return toView(repository.save(subAnswer));
	}
	
	private List<SubmissionAnswerView> toViewList(List<SubmissionAnswer> users) {
		return Objects.requireNonNullElse(users, new ArrayList<SubmissionAnswer>())
				.stream().map(this::toView).map(Optional::get).toList();
	}
	
    private Optional<SubmissionAnswerView> toView(SubmissionAnswer entity) {
        return Optional.ofNullable(entity)
                .map(answer -> SubmissionAnswerView.builder()
                        .id(answer.getId())
                        .submissionId(answer.getSubmissionId())
                        .questionId(answer.getQuestionId())
                        .givenAnswer(answer.getGivenAnswer())
                        .correct(answer.getCorrect())
                        .pointsAwarded(answer.getPointsAwarded())
                        .build()
                );
    }
    
    private SubmissionAnswer toEntity(SubmissionAnswerView view) {
        if (view == null) {
            return null;
        }
        return SubmissionAnswer.builder()
                .id(view.getId())
                .submissionId(view.getSubmissionId())
                .questionId(view.getQuestionId())
                .givenAnswer(view.getGivenAnswer())
                .correct(view.getCorrect())
                .pointsAwarded(view.getPointsAwarded())
                .build();
    }

}
