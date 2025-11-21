package com.quiz.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quiz.common.view.QuizView;
import com.quiz.common.view.SubmissionView;
import com.quiz.model.Quiz;
import com.quiz.repositories.QuizRepository;
import com.quiz.service.QuizService;
import com.quiz.utils.DomainConverter;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;

@Service
public class QuizServiceImpl implements QuizService, DomainConverter<Quiz, QuizView> {
	
	@Autowired 
	private QuizRepository repository;
	
//	@Transactional
//	  @KafkaListener(topics = "quiz-question-events", groupId = "quiz-service-group")
//	    public void handleQuestionEvent(QuestionEvent event) {
//	        Quiz quiz=repository.findById(event.getQuizId()).get();
//	        quiz.setQuestionCount(quiz.getQuestionCount()+1);
//	        quiz =repository.save(quiz);
//	    }
	
	@Override
	public QuizView saveQuiz(QuizView view ) {
		Optional<Quiz> quizOpt = Optional.ofNullable(toDomain(view));
		 if(quizOpt.isPresent()) {
			 Quiz quiz = quizOpt.get();
			 if(quiz.getId() ==null||quiz.getId() == 0) {
				 quiz.setCreatedAt(LocalDateTime.now());
			 } else {
				 quiz.setUpdatedAt(LocalDateTime.now());
			 }
			 return toView(repository.save(quiz));
		 }
		 throw new IllegalArgumentException("Quiz view can not not be empty");
		 
	}
	
	@Override
	public QuizView startQuiz(QuizView view, SubmissionView submission) {
		view.setStartDateTime(LocalDateTime.now());
		view.setUpdatedAt(LocalDateTime.now());
		return saveQuiz(view);
	}
	

	
	@Override
	@CircuitBreaker(name = "stop-quiz", fallbackMethod = "onStopQuizFailure")
	public QuizView submitQuiz(QuizView view, CompletableFuture<SubmissionView> submissionFuture) {

	    SubmissionView submission = submissionFuture.exceptionally(ex -> {
	        throw new RuntimeException(ex); // ⬅ FUTURE FAILURES PROPAGATE TO CIRCUIT BREAKER
	    }).join(); // ⬅ non-blocking join
		view.setUpdatedAt(LocalDateTime.now());
		return saveQuiz(view);
	}
	public QuizView onStopQuizFailure(QuizView view, CompletableFuture<SubmissionView> submissionFuture, Throwable t) {
		 System.err.println("Fallback-1: " + t.getMessage());
		    return new QuizView(); // or custom fallback object
	}
	@Override
	public QuizView getQuizById(Long id ) {
		if(id != null) {
			return toView(repository.findById(id).orElseThrow( () ->new EntityNotFoundException("Quiz not found by id: " + id)));
		}
		return null;
	}
	@Override
	public List<QuizView> getQuiz() {
		return repository.findAll().stream().map(this::toView).toList();
	}
	
	@Override
	public QuizView toView(Quiz quiz) {    
		  QuizView view = new QuizView();
		    view.setId(quiz.getId());
		    view.setTitle(quiz.getTitle());
		    view.setDescription(quiz.getDescription());
		    view.setCategory(quiz.getCategory());
		    view.setDifficulty(quiz.getDifficulty());
		    view.setIspublished(quiz.getIsPublished());
		    view.setIsTimed(quiz.getIsTimed());
		    view.setDurationInMinutes(quiz.getDurationInMinutes());
		    view.setTotalPoints(quiz.getTotalPoints());
		    view.setQuestionCount(quiz.getQuestionCount());
		    view.setCreator(quiz.getCreator());
		    view.setStartDateTime(quiz.getStartDateTime());
		    view.setEndDateTime(quiz.getEndDateTime());
		    return view;
	}
	
	@Override
	public Quiz toDomain(QuizView view) {
		  Quiz quiz = new Quiz();
		    quiz.setId(view.getId());
		    quiz.setTitle(view.getTitle());
		    quiz.setDescription(view.getDescription());
		    quiz.setCategory(view.getCategory());
		    quiz.setDifficulty(view.getDifficulty());
		    quiz.setIsPublished(view.getIspublished());
		    quiz.setIsTimed(view.getIsTimed());
		    quiz.setDurationInMinutes(view.getDurationInMinutes());
		    quiz.setTotalPoints(view.getTotalPoints());
		    quiz.setQuestionCount(view.getQuestionCount());
		    quiz.setCreator(view.getCreator());
		    quiz.setStartDateTime(view.getStartDateTime());
		    quiz.setEndDateTime(view.getEndDateTime());
		    quiz.setCreatedAt(view.getCreatedAt());
		    quiz.setUpdatedAt(view.getUpdatedAt());
		    return quiz;
	}
}
