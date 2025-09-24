package com.submission.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.submission.model.SubmissionAnswer;

public interface SubmissionAnswerRepo extends JpaRepository<SubmissionAnswer, Long> {
//	List<SubmissionAnswer> findAllByQuizIdAndUser(Long quizId, Long userId);
}
