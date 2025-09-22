package com.quiz.view;

import java.time.LocalDateTime;
import java.util.List;


public class QuizView {

	    private Long id;

	    private String title;

	    private String description;

	    private String category; // e.g., "Science", "Math", "History"

	    private String difficulty; // "Easy", "Medium", "Hard"

	    private Boolean isPublished = false;

	    private Boolean isTimed = false;

	    private Integer durationInMinutes; // Total time for the quiz

	    private Integer totalPoints; // Optional: sum of question points

	    private Integer questionCount; // Optional: number of questions

	    private String creator; // User or admin who created the quiz

	    private LocalDateTime startDateTime; // Optional: quiz availability start

	    private LocalDateTime endDateTime;   // Optional: quiz availability end

	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;
	    private List<QuestionView> questions;

		public QuizView() {
			super();
			// TODO Auto-generated constructor stub
		}

		public QuizView(Long id, String title, String description, String category, String difficulty, Boolean isPublished,
				Boolean isTimed, Integer durationInMinutes, Integer totalPoints, Integer questionCount, String creator,
				LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime createdAt, LocalDateTime updatedAt) {
			super();
			this.id = id;
			this.title = title;
			this.description = description;
			this.category = category;
			this.difficulty = difficulty;
			this.isPublished = isPublished;
			this.isTimed = isTimed;
			this.durationInMinutes = durationInMinutes;
			this.totalPoints = totalPoints;
			this.questionCount = questionCount;
			this.creator = creator;
			this.startDateTime = startDateTime;
			this.endDateTime = endDateTime;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}
	    // Constructors, getters, setters
		
		  public Long getId() {
				return id;
			}

			public void setId(Long id) {
				this.id = id;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}

			public String getCategory() {
				return category;
			}

			public void setCategory(String category) {
				this.category = category;
			}

			public String getDifficulty() {
				return difficulty;
			}

			public void setDifficulty(String difficulty) {
				this.difficulty = difficulty;
			}

			public Boolean getIsPublished() {
				return isPublished;
			}

			public void setIsPublished(Boolean isPublished) {
				this.isPublished = isPublished;
			}

			public Boolean getIsTimed() {
				return isTimed;
			}

			public void setIsTimed(Boolean isTimed) {
				this.isTimed = isTimed;
			}

			public Integer getDurationInMinutes() {
				return durationInMinutes;
			}

			public void setDurationInMinutes(Integer durationInMinutes) {
				this.durationInMinutes = durationInMinutes;
			}

			public Integer getTotalPoints() {
				return totalPoints;
			}

			public void setTotalPoints(Integer totalPoints) {
				this.totalPoints = totalPoints;
			}

			public Integer getQuestionCount() {
				return questionCount;
			}

			public void setQuestionCount(Integer questionCount) {
				this.questionCount = questionCount;
			}

			public String getCreator() {
				return creator;
			}

			public void setCreator(String creator) {
				this.creator = creator;
			}

			public LocalDateTime getStartDateTime() {
				return startDateTime;
			}

			public void setStartDateTime(LocalDateTime startDateTime) {
				this.startDateTime = startDateTime;
			}

			public LocalDateTime getEndDateTime() {
				return endDateTime;
			}

			public void setEndDateTime(LocalDateTime endDateTime) {
				this.endDateTime = endDateTime;
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

			public List<QuestionView> getQuestions() {
				return questions;
			}

			public void setQuestions(List<QuestionView> questions) {
				this.questions = questions;
			}
	}


