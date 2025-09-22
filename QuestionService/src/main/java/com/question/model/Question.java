package com.question.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "questions")
public class Question {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Long quizId; // ID from Quiz Service

	    private String text; // The actual question

	    @ElementCollection(fetch = FetchType.EAGER)
	    private List<String> options; // Multiple-choice options

	    private String correctAnswer; // Could also be an index (e.g., "A", "1")

	    private String explanation; // Optional: Explain the correct answer

	    private String type; // "MCQ", "TrueFalse", "ShortAnswer", etc.

	    private Integer points; // Score for this question

	    private Integer timeLimitInSeconds; // Optional: time per question

	    private Boolean isActive; // For soft delete or visibility

	    private String difficulty; // "Easy", "Medium", "Hard"

	    private String category; // "Math", "History", etc.

	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;
	    
	    
		public Question() {
			super();
		}


		public Question(Long id, Long quizId, String text, List<String> options, String correctAnswer,
				String explanation, String type, Integer points, Integer timeLimitInSeconds, Boolean isActive,
				String difficulty, String category, LocalDateTime createdAt, LocalDateTime updatedAt) {
			super();
			this.id = id;
			this.quizId = quizId;
			this.text = text;
			this.options = options;
			this.correctAnswer = correctAnswer;
			this.explanation = explanation;
			this.type = type;
			this.points = points;
			this.timeLimitInSeconds = timeLimitInSeconds;
			this.isActive = isActive;
			this.difficulty = difficulty;
			this.category = category;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public Long getQuizId() {
			return quizId;
		}


		public void setQuizId(Long quizId) {
			this.quizId = quizId;
		}


		public String getText() {
			return text;
		}


		public void setText(String text) {
			this.text = text;
		}


		public List<String> getOptions() {
			return options;
		}


		public void setOptions(List<String> options) {
			this.options = options;
		}


		public String getCorrectAnswer() {
			return correctAnswer;
		}


		public void setCorrectAnswer(String correctAnswer) {
			this.correctAnswer = correctAnswer;
		}


		public String getExplanation() {
			return explanation;
		}


		public void setExplanation(String explanation) {
			this.explanation = explanation;
		}


		public String getType() {
			return type;
		}


		public void setType(String type) {
			this.type = type;
		}


		public Integer getPoints() {
			return points;
		}


		public void setPoints(Integer points) {
			this.points = points;
		}


		public Integer getTimeLimitInSeconds() {
			return timeLimitInSeconds;
		}


		public void setTimeLimitInSeconds(Integer timeLimitInSeconds) {
			this.timeLimitInSeconds = timeLimitInSeconds;
		}


		public Boolean getIsActive() {
			return isActive;
		}


		public void setIsActive(Boolean isActive) {
			this.isActive = isActive;
		}


		public String getDifficulty() {
			return difficulty;
		}


		public void setDifficulty(String difficulty) {
			this.difficulty = difficulty;
		}


		public String getCategory() {
			return category;
		}


		public void setCategory(String category) {
			this.category = category;
		}


		public LocalDateTime getCreatedAt() {
			return createdAt;
		}


		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}


		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}


		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}
	    
	
}
