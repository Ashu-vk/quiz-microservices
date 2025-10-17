package com.submission.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.submission.model.SubmissionAnswer;

public interface SubmissionAnswerRepo extends JpaRepository<SubmissionAnswer, Long> {
	Optional<SubmissionAnswer> findByQuestionIdAndSubmissionId(Long questionId, Long SubmissionId);
	List<SubmissionAnswer> findBySubmissionId(Long id);
} 
