package com.quiz.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ErrorView {

	private LocalDateTime timestamp;
	private Integer status;
	private String error;
}
