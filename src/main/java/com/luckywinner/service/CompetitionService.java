package com.luckywinner.service;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.luckywinner.dto.AnswerRequest;
import com.luckywinner.dto.AnswerResponse;
import com.luckywinner.dto.CodeEntryResponse;
import com.luckywinner.dto.CompetitionResponse;
import com.luckywinner.dto.QuestionResponse;
import com.luckywinner.entity.Competition;
import com.luckywinner.entity.CompetitionParticipation;
import com.luckywinner.entity.Question;
import com.luckywinner.entity.User;
import com.luckywinner.entity.WalletTransaction;
import com.luckywinner.repository.CompetitionParticipationRepository;
import com.luckywinner.repository.CompetitionRepository;
import com.luckywinner.repository.QuestionRepository;
import com.luckywinner.repository.UserRepository;
import com.luckywinner.repository.WalletTransactionRepository;

@Service
public class CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final QuestionRepository questionRepository;
    private final CompetitionParticipationRepository participationRepository;
    private final UserRepository userRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    public CompetitionService(CompetitionRepository competitionRepository,
                              QuestionRepository questionRepository,
                              CompetitionParticipationRepository participationRepository,
                              UserRepository userRepository,
                              WalletTransactionRepository walletTransactionRepository) {
        this.competitionRepository = competitionRepository;
        this.questionRepository = questionRepository;
        this.participationRepository = participationRepository;
        this.userRepository = userRepository;
        this.walletTransactionRepository = walletTransactionRepository;
    }

    // گرفتن کاربر لاگین شده
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No authenticated user");
        }

        String phone = auth.getName();
        User user = userRepository.findByPhone(phone);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // آپدیت lastActiveAt
        user.setLastActiveAt(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }

    // مسابقه‌ی در حال اجرا
    private Competition getCurrentRunningCompetition() {
        return competitionRepository
                .findFirstByStatusOrderByStartTimeDesc("RUNNING")
                .orElseThrow(() -> new RuntimeException("No running competition"));
    }
    
 // ماسک کردن شماره تلفن: فقط 4 رقم آخر را نشان می‌دهیم
    private String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) {
            return "********";
        }
        String last4 = phone.substring(phone.length() - 4);
        return "********" + last4;
    }



    public CompetitionResponse getCurrentCompetition() {
        Competition c = getCurrentRunningCompetition();

        return new CompetitionResponse(
                c.getId(),
                c.getTitle(),
                c.getDescription(),
                c.getRoundNumber(),
                c.getStartTime(),
                c.getEndTime(),
                c.getStatus()
        );
    }

    public QuestionResponse getCurrentQuestion() {
        Competition c = getCurrentRunningCompetition();
        Question q = questionRepository
                .findByCompetition(c)
                .orElseThrow(() -> new RuntimeException("No question for this competition"));

        return new QuestionResponse(
                q.getQuestionText(),
                q.getOptionA(),
                q.getOptionB(),
                q.getOptionC(),
                q.getOptionD()
        );
    }

    public AnswerResponse answerCurrentQuestion(AnswerRequest request) {
        // مسابقه در حال اجرا و کاربر لاگین‌شده
        Competition c = getCurrentRunningCompetition();
        User user = getCurrentUser();

        // سوال این مسابقه
        Question q = questionRepository
                .findByCompetition(c)
                .orElseThrow(() -> new RuntimeException("No question for this competition"));

        String selected = request.getOption();
        if (selected == null) {
            return new AnswerResponse(false, "Option is required", null);
        }

        selected = selected.trim().toUpperCase();

        // ✅ چک: آیا قبلاً برای این مسابقه شرکت کرده است؟
        boolean alreadyJoined =
                participationRepository.existsByUserAndCompetition(user, c);

        if (alreadyJoined) {
            throw new IllegalStateException("شما قبلاً در این مسابقه شرکت کرده‌اید.");
        }

        // participation قبلی کاربر در این مسابقه (احتمالاً وجود ندارد)
        CompetitionParticipation participation =
                participationRepository
                        .findByCompetitionIdAndUserId(c.getId(), user.getId())
                        .orElse(null);

        if (participation == null) {
            participation = new CompetitionParticipation();
            participation.setCompetition(c);
            participation.setUser(user);
        }

        boolean isCorrect = selected.equals(q.getCorrectOption());

        participation.setAnsweredCorrect(isCorrect);
        participationRepository.save(participation);

        if (isCorrect) {
            // کد جایزه را از خود competition می‌خوانیم
            String prizeCode = c.getCorrectCode();
            String msg = "جواب درست است، می‌توانید کد را در صفحه مخصوص وارد کنید.";
            return new AnswerResponse(true, msg, prizeCode);
        } else {
            return new AnswerResponse(false, "جواب اشتباه است.", null);
        }
    }


    // -----------------------------
    // ❌ بخش مربوط به «تبلیغ» فعلاً غیرفعال شده
    //   اگر بعداً خواستی فعال کنی، این متد را از حالت کامنت دربیاور.
    // -----------------------------
    /*
    // ثبت دیدن تبلیغ برای رقابت جاری
    public CodeEntryResponse markAdWatchedForCurrentCompetition() {

        Competition competition = getCurrentRunningCompetition();
        User user = getCurrentUser();

        // باید قبلاً سوال این رقابت را درست جواب داده باشد
        CompetitionParticipation participation =
                participationRepository
                        .findByCompetitionIdAndUserId(competition.getId(), user.getId())
                        .orElse(null);

        if (participation == null || !participation.isAnsweredCorrect()) {
            return new CodeEntryResponse(false, "شما هنوز سوال را درست جواب نداده‌اید.");
        }

        // اگر قبلاً ثبت شده باشد
        if (participation.isWatchedAd()) {
            return new CodeEntryResponse(false, "قبلاً مشاهده تبلیغ برای شما ثبت شده است.");
        }

        participation.setWatchedAd(true);
        participationRepository.save(participation);

        return new CodeEntryResponse(true, "مشاهده تبلیغ با موفقیت ثبت شد؛ حالا می‌توانید کد را وارد کنید.");
    }
    */

    // --------- API: وارد کردن کد درست و برنده شدن (فعال می‌ماند) ---------
 // وارد کردن کد صحیح مسابقه
    public CodeEntryResponse enterCorrectCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return new CodeEntryResponse(false, "کد نمی‌تواند خالی باشد.");
        }

        code = code.trim();

        Competition competition = getCurrentRunningCompetition();
        User user = getCurrentUser();

        // پیدا کردن participation فعلی برای این مسابقه
        CompetitionParticipation participation =
                participationRepository
                        .findByCompetitionIdAndUserId(competition.getId(), user.getId())
                        .orElse(null);

        if (participation == null || !participation.isAnsweredCorrect()) {
            return new CodeEntryResponse(false, "شما هنوز سوال را درست جواب نداده‌اید.");
        }

        String correctCode = competition.getCorrectCode();
        if (correctCode == null || correctCode.isBlank()) {
            return new CodeEntryResponse(false, "کد صحیح برای این رقابت هنوز تنظیم نشده است.");
        }

        if (!correctCode.equals(code)) {
            return new CodeEntryResponse(false, "کد وارد شده اشتباه است.");
        }

        // آیا قبلاً برنده‌ای برای این رقابت ثبت شده؟
        CompetitionParticipation existingWinner =
                participationRepository
                        .findAll()
                        .stream()
                        .filter(p -> p.getCompetition().getId().equals(competition.getId()) && p.isWinner())
                        .findFirst()
                        .orElse(null);

        if (existingWinner != null) {
            // یعنی یکی قبلاً برنده شده
            User winnerUser = existingWinner.getUser();

            CodeEntryResponse resp = new CodeEntryResponse(
                    false,
                    "متاسفانه برنده این رقابت قبلاً مشخص شده است."
            );

            // اطلاعات برنده را هم برگردانیم
            resp.setWinnerName(winnerUser.getFullName());
            resp.setWinnerPhoneMasked(maskPhone(winnerUser.getPhone()));

            // اگر خودِ همین کاربر برنده قبلی باشد
            if (winnerUser.getId().equals(user.getId())) {
                resp.setSuccess(true);
                resp.setMessage("شما قبلاً برنده این رقابت شده‌اید. تبریک!");
            }

            return resp;
        }

        // اگر هنوز برنده‌ای ثبت نشده → این کاربر برنده می‌شود
        participation.setWatchedAd(true);        // فلگ نمایشی
        participation.setEnteredCode(code);
        participation.setEnterTime(LocalDateTime.now());
        participation.setWinner(true);
        participationRepository.save(participation);

        // 10 دالر به کیف پول اضافه کنیم
        double prize = 10.0;
        user.setBalance(user.getBalance() + prize);
        user.setTotalWon(user.getTotalWon() + prize);
        user.setLastActiveAt(LocalDateTime.now());
        userRepository.save(user);

        // ثبت تراکنش کیف پول
        WalletTransaction tx = new WalletTransaction(user, prize, "WIN");
        walletTransactionRepository.save(tx);

        return new CodeEntryResponse(true, "تبریک! شما برنده 10 دالر شدید.");
    }
    
    // حذف یک سوال
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
    }




}
