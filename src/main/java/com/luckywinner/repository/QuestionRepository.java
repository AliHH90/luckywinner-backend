package com.luckywinner.repository;

import com.luckywinner.entity.Competition;
import com.luckywinner.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByCompetition(Competition competition);

    // نکته مهم: برای فیلد competition.id باید از _ استفاده کنیم
    // قبلاً deleteByCompetitionId بود و به خاطر نام اشتباه هیچ‌چیز حذف نمی‌شد
    void deleteByCompetitionId(Long competitionId);
}
