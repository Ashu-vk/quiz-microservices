package com.question.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.question.model.Question;
import com.question.repositories.QuestionRepository;
import com.question.services.QuestionService;
import com.quiz.common.view.QuestionEvent;
import com.quiz.common.view.QuestionView;
import com.quiz.common.view.QuizView;
import com.quiz.common.view.SubmissionAnswerView;
import com.quiz.common.view.SubmissionView;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionRepository repository;
	
	@Autowired
	private KafkaTemplate<String, QuestionEvent> kafkaTemplate;
	
	@Override
	public Optional<QuestionView> getById(Long id) {
		return toView(repository.findById(id).orElse(null));
	}
	
	@Override
	public List<QuestionView> getAll() {
		return toViewList(repository.findAll());
	}
	@Override
	public List<QuestionView> getAllByQuizId(Long quizId) {
		return toViewList(repository.findByQuizId(quizId));
	}
	

	@Override
	public QuestionView saveOrUpdateQuestionForQuiz(Long quizId, QuestionView questionView) {
		boolean isNew = false;
		if(questionView==null) {
			throw new IllegalArgumentException("Question can not be empty");
		}
		if(questionView.getId()==null && (questionView.getQuiz()!=null && questionView.getQuiz().getId().equals(quizId))) {
			isNew = true;
		}
			questionView.setQuiz(questionView.getQuiz());
		questionView = saveOrUpdateQuestion(questionView);
		QuestionEvent qEvent = QuestionEvent.builder().question(questionView)
				.quiz(questionView.getQuiz())
				.content(isNew?"Added new question": "Updated question with id "+ questionView.getId())
				.timestamp(LocalDateTime.now()).build();
		kafkaTemplate.send("quiz-question-events", quizId.toString(), qEvent);
		return questionView;
	}
	
	@Override
	public QuestionView saveOrUpdateQuestion(QuestionView view) {
		if(Objects.requireNonNullElse(Objects.requireNonNullElse(view, new QuestionView()).getText(), "").trim().isEmpty()) {
			throw new IllegalArgumentException("Question can not be empty");
		}
		Question  question = null;
		if(Objects.requireNonNullElse(view.getId(), 0L) == 0) {
			question = new Question();
			question.setCreatedAt(LocalDateTime.now());
		} else {
			question = repository.findById(view.getId()).get();
		}
		question = toDomain(view, question);
		question.setUpdatedAt(LocalDateTime.now());
		return toView(repository.save(question)).get();
	}
	
	private List<QuestionView> toViewList(List<Question> qList) {
		return Objects.requireNonNullElse(qList, new ArrayList<Question>())
				.stream().map(this::toView).filter(Optional::isPresent)
				.map(Optional::get).toList();
	}
	
	public Optional<QuestionView> toView(Question question) {
		if(question==null) {
			return Optional.empty();
		}
		QuestionView qView = new QuestionView();
		qView.setId(question.getId());
		qView.setCategory(question.getCategory());
		qView.setCreatedAt(question.getCreatedAt());
		qView.setQuiz(QuizView.builder().id(question.getQuizId()).build());
		qView.setDifficulty(question.getDifficulty());
		qView.setOptions(question.getOptions());
		qView.setText(question.getText());
		qView.setTimeLimitInSeconds(question.getTimeLimitInSeconds());
		qView.setIsActive(question.getIsActive());
		qView.setType(question.getType());
		qView.setUpdatedAt(question.getUpdatedAt());
		qView.setPoints(question.getPoints());
		return Optional.of(qView) ;
	}
	
	public Question toDomain(QuestionView view, Question ques) {
		ques.setId(view.getId());
		ques.setCategory(view.getCategory());
		ques.setCorrectAnswer(view.getCorrectAnswer());
		ques.setCreatedAt(view.getCreatedAt());
		ques.setDifficulty(view.getDifficulty());
		ques.setExplanation(view.getExplanation());
		ques.setIsActive(view.getIsActive());
		ques.setOptions(view.getOptions());
		ques.setPoints(view.getPoints());
		ques.setText(view.getText());
		ques.setTimeLimitInSeconds(view.getTimeLimitInSeconds());
		ques.setType(view.getType());
		ques.setUpdatedAt(view.getUpdatedAt());
		return ques;
	}

	@Override
	public SubmissionView verifySubmission(SubmissionView submission) throws Exception {
		if(submission==null) {
			throw new Exception("submission provided to verify is null");
		}
		for(SubmissionAnswerView view: submission.getAnswers()) {
			Question question = repository.findById(view.getQuestion().getId()).orElseThrow(()-> new Exception("No question associated with this answer"));
			if(question.getCorrectAnswer()==null ||question.getCorrectAnswer().isBlank()) {
				if(view.getGivenAnswer().equals(question.getCorrectAnswer())) {
					view.setCorrect(true);
					view.setPointsAwarded(question.getPoints());
				} else {
					view.setCorrect(false);
					view.setPointsAwarded(0);
				}
			} else {
				throw new Exception("Invalid question");
			}
		}
		return submission;
	}

}
