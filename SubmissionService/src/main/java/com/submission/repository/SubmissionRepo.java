package com.submission.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.submission.model.Submission;

public interface SubmissionRepo extends JpaRepository<Submission, Long> {
	List<Submission> findByQuizId(Long quizId);
	List<Submission> findByUserId(Long userId);
	Optional<Submission> findByQuizIdAndUserId(Long quizId, Long userId);
}
