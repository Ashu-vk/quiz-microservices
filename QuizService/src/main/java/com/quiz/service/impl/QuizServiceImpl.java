package com.quiz.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.quiz.model.Quiz;
import com.quiz.repositories.QuizRepository;
import com.quiz.service.QuizService;
import com.quiz.utils.DomainConverter;
import com.quiz.view.QuestionEvent;
import com.quiz.view.QuizView;

import jakarta.transaction.Transactional;

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
		Optional<Quiz> quiz = Optional.ofNullable(toDomain(view));
		 if(quiz.isPresent()) {
			 return toView(repository.save(quiz.get()));
		 }
		 throw new IllegalArgumentException("Quiz view can not not be empty");
		 
	}
	@Override
	public QuizView getQuizById(Long id ) {
		if(id != null) {
			return toView(repository.findById(id).orElseThrow( () ->new RuntimeException("Quiz not found by id" + id)));
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
		    view.setIsPublished(quiz.getIsPublished());
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
		    quiz.setIsPublished(view.getIsPublished());
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
