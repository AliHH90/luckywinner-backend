package com.luckywinner.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "competition_participations")
public class CompetitionParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // رقابت
    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    // کاربر
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // آیا سوال را درست جواب داده؟
    @Column(nullable = false)
    private boolean answeredCorrect;

    // آیا تبلیغ را دیده است؟ (در مراحل بعدی استفاده می‌کنیم)
    @Column(nullable = false)
    private boolean watchedAd = false;

    // کدی که وارد کرده
    private String enteredCode;

    // زمانی که کد را وارد کرده
    private LocalDateTime enterTime;

    // آیا برنده نهایی است؟
    @Column(nullable = false)
    private boolean winner = false;

    public CompetitionParticipation() {
    }

    // ---- Getters & Setters ----

    public Long getId() {
        return id;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAnsweredCorrect() {
        return answeredCorrect;
    }

    public void setAnsweredCorrect(boolean answeredCorrect) {
        this.answeredCorrect = answeredCorrect;
    }

    public boolean isWatchedAd() {
        return watchedAd;
    }

    public void setWatchedAd(boolean watchedAd) {
        this.watchedAd = watchedAd;
    }

    public String getEnteredCode() {
        return enteredCode;
    }

    public void setEnteredCode(String enteredCode) {
        this.enteredCode = enteredCode;
    }

    public LocalDateTime getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(LocalDateTime enterTime) {
        this.enterTime = enterTime;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
