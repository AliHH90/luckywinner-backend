package com.luckywinner.repository;

import com.luckywinner.entity.Competition;
import com.luckywinner.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByCompetition(Competition competition);
}
