package com.luckywinner.repository;

import com.luckywinner.entity.CompetitionParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import com.luckywinner.entity.Competition;
import com.luckywinner.entity.User;
import java.util.List;
import java.util.Optional;

public interface CompetitionParticipationRepository extends JpaRepository<CompetitionParticipation, Long> {

    Optional<CompetitionParticipation> findByCompetitionIdAndUserId(Long competitionId, Long userId);

    Optional<CompetitionParticipation> findFirstByCompetitionIdAndWinnerTrueOrderByEnterTimeAsc(Long competitionId);
    
    List<CompetitionParticipation> findByCompetitionId(Long competitionId);

    List<CompetitionParticipation> findTop20ByWinnerTrueOrderByEnterTimeDesc();
    
    void deleteByCompetitionId(Long competitionId);
    
    // اضافه شد: حذف تمام مشارکت‌های یک کاربر
    void deleteByUserId(Long userId);
    
    // آیا کاربر برای این مسابقه قبلاً شرکت کرده است؟
    boolean existsByUserAndCompetition(User user, Competition competition);

}
