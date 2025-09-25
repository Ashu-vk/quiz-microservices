package com.quiz.common.view;

import lombok.*;

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

