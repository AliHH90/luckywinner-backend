package com.luckywinner.repository;

import com.luckywinner.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.luckywinner.entity.User;

import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    // جمع کل مبلغ تراکنش‌های از یک نوع خاص (مثلاً PAYOUT)
    @Query("select coalesce(sum(w.amount), 0) from WalletTransaction w where w.type = :type")
    double sumAmountByType(@Param("type") String type);
    
    // اضافه شد: حذف تمام تراکنش‌های یک کاربر
    void deleteByUserId(Long userId);
    
    // ✅ لیست تراکنش‌های یک کاربر (جدیدترین اول)
    List<WalletTransaction> findTop30ByUserOrderByCreatedAtDesc(User user);
}
