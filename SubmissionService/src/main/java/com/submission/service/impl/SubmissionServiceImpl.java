package com.submission.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.submission.model.Submission;
import com.submission.repository.SubmissionRepo;
import com.submission.service.SubmissionService;
import com.submission.view.SubmissionView;

public class SubmissionServiceImpl implements SubmissionService {

	@Autowired
	private SubmissionRepo repository;
	
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
	public Optional<SubmissionView> getSubmissionById(Long id) {
		return toView(repository.findById(id).get());
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
                .quizId(view.getQuizId())
                .userId(view.getUserId())
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
                        .quizId(answer.getQuizId())
                        .userId(answer.getUserId())
                        .startTime(answer.getStartTime())
                        .endTime(answer.getEndTime())
                        .score(answer.getScore())
                        .totalPoints(answer.getTotalPoints())
                        .status(answer.getStatus())
                        .build()
                );
    }
}
