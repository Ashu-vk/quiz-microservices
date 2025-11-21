package com.quiz.common.view;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionAnswerView {
    private Long id;
    private SubmissionView submission;
    private QuestionView question;
    private String givenAnswer;
    private Boolean correct;
    private Integer pointsAwarded;
}

