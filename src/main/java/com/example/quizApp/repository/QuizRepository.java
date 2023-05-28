package com.example.quizApp.repository;

import com.example.quizApp.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
    List<Quiz> findByStatus(String active);
}
