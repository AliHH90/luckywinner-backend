package com.luckywinner.repository;

import com.luckywinner.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    // آخرین مسابقه‌ای که status = 'RUNNING' دارد و آخرین startTime
    Optional<Competition> findFirstByStatusOrderByStartTimeDesc(String status);

    // برای شمردن مسابقات امروز
    long countByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}
