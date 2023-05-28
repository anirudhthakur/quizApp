package com.example.quizApp.controller;
import com.example.quizApp.entity.Quiz;
import com.example.quizApp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {
    private final QuizRepository quizRepository;

    @Autowired
    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        quiz.setStatus("inactive"); // Set initial status
        return quizRepository.save(quiz);
    }

    @GetMapping("/active")
    public List<Quiz> getActiveQuiz() {
        return quizRepository.findByStatus("active");
    }

    @GetMapping("/{id}/result")
    public int getQuizResult(@PathVariable("id") Long id) throws ChangeSetPersister.NotFoundException {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));

        if (quiz.getStatus().equals("finished")) {
            return quiz.getRightAnswer();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quiz is not finished yet.");
        }
    }

    @GetMapping("/all")
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }
}
