package com.luckywinner.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckywinner.dto.AdminCreateCompetitionRequest;
import com.luckywinner.dto.AdminQuestionRequest;
import com.luckywinner.dto.CompetitionResponse;
import com.luckywinner.dto.ParticipantResponse;
import com.luckywinner.dto.QuestionResponse;
import com.luckywinner.dto.WinnerResponse;
import com.luckywinner.entity.Competition;
import com.luckywinner.entity.CompetitionParticipation;
import com.luckywinner.entity.Question;
import com.luckywinner.entity.User;
import com.luckywinner.repository.CompetitionParticipationRepository;
import com.luckywinner.repository.CompetitionRepository;
import com.luckywinner.repository.QuestionRepository;
import com.luckywinner.repository.UserRepository;

@Service
public class AdminCompetitionService {

    private final CompetitionRepository competitionRepository;
    private final CompetitionParticipationRepository participationRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public AdminCompetitionService(CompetitionRepository competitionRepository,
                                   CompetitionParticipationRepository participationRepository,
                                   QuestionRepository questionRepository,
                                   UserRepository userRepository) {
        this.competitionRepository = competitionRepository;
        this.participationRepository = participationRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    private String getCurrentAdminPhone() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No authenticated admin");
        }
        return auth.getName();
    }

    // ---------- ایجاد مسابقه جدید ----------
    public CompetitionResponse createCompetition(AdminCreateCompetitionRequest request) {

        String creatorPhone = getCurrentAdminPhone();

        Competition c = new Competition();
        c.setTitle(request.getTitle());
        c.setDescription(request.getDescription());
        c.setRoundNumber(request.getRoundNumber());
        c.setStartTime(request.getStartTime() != null ? request.getStartTime() : LocalDateTime.now());
        c.setEndTime(request.getEndTime() != null ? request.getEndTime() : LocalDateTime.now().plusMinutes(30));

        String status = request.getStatus();
        if (status == null || status.isBlank()) {
            status = "RUNNING";
        }
        c.setStatus(status);
        c.setCreatedBy(creatorPhone);

        Competition saved = competitionRepository.save(c);

        return new CompetitionResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getRoundNumber(),
                saved.getStartTime(),
                saved.getEndTime(),
                saved.getStatus()
        );
    }
    
    @Transactional
    public void deleteQuestion(Long competitionId) {
        // اگر خواستی مطمئن شوی مسابقه وجود دارد:
        // competitionRepository.findById(competitionId)
        //        .orElseThrow(() -> new RuntimeException("Competition not found"));

        // همه سؤال‌های مربوط به این مسابقه (در عمل معمولاً یک سؤال) حذف می‌شود
        questionRepository.deleteByCompetitionId(competitionId);
    }
    
 // حذف کامل مسابقه + سوال‌ها + مشارکت‌ها
    @Transactional
    public void deleteCompetition(Long competitionId) {
        // اول مشارکت‌ها
        participationRepository.deleteByCompetitionId(competitionId);
        // بعد سوال‌ها
        questionRepository.deleteByCompetitionId(competitionId);
        // آخر خود مسابقه
        competitionRepository.deleteById(competitionId);
    }


    // ---------- لیست مسابقات ----------
    public List<CompetitionResponse> getAllCompetitions() {
        return competitionRepository.findAll()
                .stream()
                .map(c -> new CompetitionResponse(
                        c.getId(),
                        c.getTitle(),
                        c.getDescription(),
                        c.getRoundNumber(),
                        c.getStartTime(),
                        c.getEndTime(),
                        c.getStatus()
                ))
                .collect(Collectors.toList());
    }

    // ---------- تنظیم/تغییر correctCode ----------
    public void setCorrectCode(Long competitionId, String correctCode) {
        Competition c = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        c.setCorrectCode(correctCode);
        competitionRepository.save(c);
    }

    // ---------- تغییر وضعیت مسابقه ----------
    public void updateStatus(Long competitionId, String status) {
        Competition c = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        c.setStatus(status);
        competitionRepository.save(c);
    }

    // ---------- لیست شرکت‌کنندگان یک مسابقه ----------
    public List<ParticipantResponse> getParticipants(Long competitionId) {

        List<CompetitionParticipation> participations =
                participationRepository.findByCompetitionId(competitionId);

        return participations.stream()
                .map(p -> {
                    User u = p.getUser();
                    return new ParticipantResponse(
                            u.getId(),
                            u.getPhone(),
                            u.getFullName(),
                            p.isAnsweredCorrect(),
                            p.isWatchedAd(),
                            p.isWinner(),
                            p.getEnterTime()
                    );
                })
                .collect(Collectors.toList());
    }

    // ---------- آخرین برنده‌ها (عمومی) ----------
    public List<WinnerResponse> getLatestWinners(int limit) {
        return participationRepository
                .findTop20ByWinnerTrueOrderByEnterTimeDesc()
                .stream()
                .limit(limit)
                .map(p -> {
                    User u = p.getUser();
                    Competition c = p.getCompetition();
                    // فعلاً جایزه را ثابت 10 در نظر می‌گیریم
                    return new WinnerResponse(
                            u.getId(),
                            u.getPhone(),
                            u.getFullName(),
                            10.0,
                            c.getId(),
                            c.getTitle(),
                            p.getEnterTime()
                    );
                })
                .collect(Collectors.toList());
    }

    // ---------- گرفتن سؤال مسابقه برای پنل ادمین ----------
    public QuestionResponse getQuestion(Long competitionId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        return questionRepository
                .findByCompetition(competition)
                .map(q -> new QuestionResponse(
                        q.getQuestionText(),
                        q.getOptionA(),
                        q.getOptionB(),
                        q.getOptionC(),
                        q.getOptionD()
                ))
                // ❗ اگر هنوز سؤالی وجود ندارد، آبجکت خالی برگردان (نه خطا)
                .orElseGet(() -> new QuestionResponse(null, null, null, null, null));
    }

    // ---------- ایجاد / ویرایش سؤال مسابقه ----------
    public QuestionResponse saveQuestion(Long competitionId, AdminQuestionRequest request) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        // اگر سؤال قبلاً وجود داشته باشد، همان را ویرایش کن؛ وگرنه جدید بساز
        Question q = questionRepository
                .findByCompetition(competition)
                .orElse(null);

        if (q == null) {
            q = new Question();
            q.setCompetition(competition);
        }

        q.setQuestionText(request.getQuestionText());
        q.setOptionA(request.getOptionA());
        q.setOptionB(request.getOptionB());
        q.setOptionC(request.getOptionC());
        q.setOptionD(request.getOptionD());
        q.setCorrectOption(request.getCorrectOption()); // "A" / "B" / ...

        Question saved = questionRepository.save(q);

        return new QuestionResponse(
                saved.getQuestionText(),
                saved.getOptionA(),
                saved.getOptionB(),
                saved.getOptionC(),
                saved.getOptionD()
        );
    }
}






//package com.luckywinner.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import com.luckywinner.dto.AdminCreateCompetitionRequest;
//import com.luckywinner.dto.AdminQuestionRequest;
//import com.luckywinner.dto.CompetitionResponse;
//import com.luckywinner.dto.ParticipantResponse;
//import com.luckywinner.dto.QuestionResponse;
//import com.luckywinner.dto.WinnerResponse;
//import com.luckywinner.entity.Competition;
//import com.luckywinner.entity.CompetitionParticipation;
//import com.luckywinner.entity.Question;
//import com.luckywinner.entity.User;
//import com.luckywinner.repository.CompetitionParticipationRepository;
//import com.luckywinner.repository.CompetitionRepository;
//import com.luckywinner.repository.QuestionRepository;
//import com.luckywinner.repository.UserRepository;
//
//@Service
//public class AdminCompetitionService {
//
//    private final CompetitionRepository competitionRepository;
//    private final CompetitionParticipationRepository participationRepository;
//    private final UserRepository userRepository;
//    private final QuestionRepository questionRepository;
//
//    public AdminCompetitionService(CompetitionRepository competitionRepository,
//                                   CompetitionParticipationRepository participationRepository,
//                                   QuestionRepository questionRepository,
//                                   UserRepository userRepository) {
//        this.competitionRepository = competitionRepository;
//        this.participationRepository = participationRepository;
//        this.userRepository = userRepository;
//        this.questionRepository = questionRepository;
//    }
//
//    // گرفتن شماره تلفن ادمین لاگین‌شده
//    private String getCurrentAdminPhone() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null || !auth.isAuthenticated()) {
//            throw new RuntimeException("No authenticated admin");
//        }
//        return auth.getName();
//    }
//
//    // ---------- ایجاد مسابقه جدید ----------
//    public CompetitionResponse createCompetition(AdminCreateCompetitionRequest request) {
//
//        String creatorPhone = getCurrentAdminPhone();
//
//        Competition c = new Competition();
//        c.setTitle(request.getTitle());
//        c.setDescription(request.getDescription());
//        c.setRoundNumber(request.getRoundNumber());
//
//        c.setStartTime(
//                request.getStartTime() != null
//                        ? request.getStartTime()
//                        : LocalDateTime.now()
//        );
//
//        c.setEndTime(
//                request.getEndTime() != null
//                        ? request.getEndTime()
//                        : LocalDateTime.now().plusMinutes(30)
//        );
//
//        String status = request.getStatus();
//        if (status == null || status.isBlank()) {
//            status = "RUNNING";
//        }
//        c.setStatus(status);
//
//        // اگر در DTO کد جایزه فرستاده شود، ذخیره‌اش می‌کنیم
//        if (request.getCorrectCode() != null && !request.getCorrectCode().isBlank()) {
//            c.setCorrectCode(request.getCorrectCode().trim());
//        }
//
//        c.setCreatedBy(creatorPhone);
//
//        Competition saved = competitionRepository.save(c);
//
//        return new CompetitionResponse(
//                saved.getId(),
//                saved.getTitle(),
//                saved.getDescription(),
//                saved.getRoundNumber(),
//                saved.getStartTime(),
//                saved.getEndTime(),
//                saved.getStatus()
//        );
//    }
//
//    // ---------- لیست مسابقات ----------
//    public List<CompetitionResponse> getAllCompetitions() {
//        return competitionRepository.findAll()
//                .stream()
//                .map(c -> new CompetitionResponse(
//                        c.getId(),
//                        c.getTitle(),
//                        c.getDescription(),
//                        c.getRoundNumber(),
//                        c.getStartTime(),
//                        c.getEndTime(),
//                        c.getStatus()
//                ))
//                .collect(Collectors.toList());
//    }
//
//    // ---------- تنظیم/تغییر correctCode ----------
//    public void setCorrectCode(Long competitionId, String correctCode) {
//        Competition c = competitionRepository.findById(competitionId)
//                .orElseThrow(() -> new RuntimeException("Competition not found"));
//
//        c.setCorrectCode(correctCode);
//        competitionRepository.save(c);
//    }
//
//    // ---------- تغییر وضعیت مسابقه ----------
//    public void updateStatus(Long competitionId, String status) {
//        Competition c = competitionRepository.findById(competitionId)
//                .orElseThrow(() -> new RuntimeException("Competition not found"));
//
//        c.setStatus(status);
//        competitionRepository.save(c);
//    }
//
//    // ---------- لیست شرکت‌کنندگان یک مسابقه ----------
//    public List<ParticipantResponse> getParticipants(Long competitionId) {
//
//        List<CompetitionParticipation> participations =
//                participationRepository.findByCompetitionId(competitionId);
//
//        return participations.stream()
//                .map(p -> {
//                    User u = p.getUser();
//                    return new ParticipantResponse(
//                            u.getId(),
//                            u.getPhone(),
//                            u.getFullName(),
//                            p.isAnsweredCorrect(),
//                            p.isWatchedAd(),
//                            p.isWinner(),
//                            p.getEnterTime()
//                    );
//                })
//                .collect(Collectors.toList());
//    }
//
//    // ---------- آخرین برنده‌ها (عمومی / برای صفحه Winners) ----------
//    public List<WinnerResponse> getLatestWinners(int limit) {
//        return participationRepository
//                .findTop20ByWinnerTrueOrderByEnterTimeDesc()
//                .stream()
//                .limit(limit)
//                .map(p -> {
//                    User u = p.getUser();
//                    Competition c = p.getCompetition();
//                    // فعلاً جایزه را ثابت 10 در نظر می‌گیریم
//                    return new WinnerResponse(
//                            u.getId(),
//                            u.getPhone(),
//                            u.getFullName(),
//                            10.0,
//                            c.getId(),
//                            c.getTitle(),
//                            p.getEnterTime()
//                    );
//                })
//                .collect(Collectors.toList());
//    }
//
//    // ---------- ساخت / ویرایش سؤال برای مسابقه ----------
//    public QuestionResponse createOrUpdateQuestion(Long competitionId,
//                                                   AdminQuestionRequest request) {
//
//        Competition competition = competitionRepository.findById(competitionId)
//                .orElseThrow(() -> new RuntimeException("Competition not found"));
//
//        // فرض: برای هر مسابقه فقط یک سؤال فعال داریم
//        Question q = questionRepository.findByCompetition(competition).orElse(null);
//        if (q == null) {
//            q = new Question();
//            q.setCompetition(competition);
//        }
//
//        q.setQuestionText(request.getQuestionText());
//        q.setOptionA(request.getOptionA());
//        q.setOptionB(request.getOptionB());
//        q.setOptionC(request.getOptionC());
//        q.setOptionD(request.getOptionD());
//        q.setCorrectOption(request.getCorrectOption());
//
//        Question saved = questionRepository.save(q);
//
//        return new QuestionResponse(
//                saved.getQuestionText(),
//                saved.getOptionA(),
//                saved.getOptionB(),
//                saved.getOptionC(),
//                saved.getOptionD()
//        );
//    }
//
//    // ---------- گرفتن سؤال مسابقه برای ادمین ----------
//    public QuestionResponse getQuestion(Long competitionId) {
//
//        Competition competition = competitionRepository.findById(competitionId)
//                .orElseThrow(() -> new RuntimeException("Competition not found"));
//
//        Question q = questionRepository.findByCompetition(competition)
//                .orElseThrow(() -> new RuntimeException("Question not found for this competition"));
//
//        return new QuestionResponse(
//                q.getQuestionText(),
//                q.getOptionA(),
//                q.getOptionB(),
//                q.getOptionC(),
//                q.getOptionD()
//        );
//    }
//}
