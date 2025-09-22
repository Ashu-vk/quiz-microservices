package com.submission.view;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionAnswerView {
    private Long id;
    private Long quizId;
    private Long submissionId;
    private Long questionId;
    private String givenAnswer;
    private Boolean correct;
    private Integer pointsAwarded;
}

