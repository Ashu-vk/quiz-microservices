package com.submission.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.quiz.common.view.QuizView;
import com.quiz.common.view.SubmissionView;
import com.quiz.common.view.UserView;
import com.submission.fign.services.UserFeignClient;
import com.submission.service.SubmissionService;

@RestController
public class RestSubmissionController {

	@Autowired
	private SubmissionService service;
	
	
	@RequestMapping(value = "/submission/{id}", method = RequestMethod.GET)
	public SubmissionView getSubById(@PathVariable(value="id") Long id) {
		try {
			return service.getSubmissionById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value = "/submission", method = RequestMethod.GET)
	public List<SubmissionView> getAll() {
		return service.getSubmissions();
	}
	@RequestMapping(value = "/submission/quiiz/{quizId}", method = RequestMethod.GET)
	public List<SubmissionView> getQuizSubmissions(@PathVariable(value = "quizId") Long quizId) {
		return service.getSubmissionsByQuiz(quizId);
	}
	
	@RequestMapping(value = "/submission/start/quiz", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public SubmissionView startSubmissions(@RequestBody QuizView quiz, @RequestParam(value = "userId") Long userId) {
		return service.startQuiz(quiz, userId);
	}
	
	@RequestMapping(value = "/submission/submit/quiz", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public SubmissionView stopSubmissions(@RequestBody QuizView quiz, @RequestParam(value = "userId") Long userId) throws Exception {
		return service.submitQuiz(quiz, userId);
	}
	
	@RequestMapping(value = "/submission/user/{userId}", method = RequestMethod.GET)
	public List<SubmissionView> getUsersSubmissions(@PathVariable(value = "userId") Long userId) {
		return service.getSubmissionsByUser(userId);
	}
	
	@RequestMapping(value = "/submission", method = RequestMethod.POST)
	public SubmissionView get(@RequestBody SubmissionView view) {
		return service.saveOrUpdateSubmission(view).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not accepted please check submission"));
	}
}
