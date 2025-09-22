package com.submission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.submission.model.SubmissionAnswer;

public interface SubmissionAnswerRepo extends JpaRepository<SubmissionAnswer, Long> {
	List<SubmissionAnswer> findAllByQuizAndUser(Long quizId, Long userId);
}
