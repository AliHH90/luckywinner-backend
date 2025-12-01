package com.luckywinner.repository;

import com.luckywinner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhone(String phone);

    boolean existsByPhone(String phone);

    // تعداد کاربرانی که در ۵ دقیقه اخیر فعال بوده‌اند (آنلاین)
    int countByLastActiveAtAfter(LocalDateTime time);

    // لیست کاربران که بالانس‌شان >= مقدار داده‌شده است
    List<User> findByBalanceGreaterThanEqual(double balance);
    
    // ✅ برترین 10 کاربر بر اساس totalWon (بیشترین برد)
    List<User> findTop10ByOrderByTotalWonDesc();
    
}
