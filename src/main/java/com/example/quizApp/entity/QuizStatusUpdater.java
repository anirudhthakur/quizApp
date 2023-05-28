package com.example.quizApp.entity;

import com.example.quizApp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class QuizStatusUpdater {
    private final QuizRepository quizRepository;

    @Autowired
    public QuizStatusUpdater(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Scheduled(cron = "0 * * * * *") // Runs every minute
    public void updateQuizStatus() {
        List<Quiz> quizzes = quizRepository.findAll();
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (Quiz quiz : quizzes) {
            LocalDateTime startDateTime = quiz.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime endDateTime = quiz.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            if (currentDateTime.isAfter(startDateTime) && currentDateTime.isBefore(endDateTime)) {
                quiz.setStatus("active");
            } else if (currentDateTime.isAfter(endDateTime)) {
                quiz.setStatus("finished");
            } else {
                quiz.setStatus("inactive");
            }
        }

        quizRepository.saveAll(quizzes);
    }
}
