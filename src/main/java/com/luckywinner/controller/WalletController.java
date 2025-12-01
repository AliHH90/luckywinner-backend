// src/main/java/com/luckywinner/controller/WalletController.java
package com.luckywinner.controller;

import com.luckywinner.dto.WalletInfoResponse;
import com.luckywinner.dto.WalletTransactionDto;
import com.luckywinner.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = "*")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // GET /api/wallet/me  → خلاصه کیف پول کاربر
    @GetMapping("/me")
    public ResponseEntity<WalletInfoResponse> getMyWallet() {
        WalletInfoResponse info = walletService.getMyWalletInfo();
        return ResponseEntity.ok(info);
    }

    // GET /api/wallet/transactions → آخرین تراکنش‌ها
    @GetMapping("/transactions")
    public ResponseEntity<List<WalletTransactionDto>> getMyTransactions() {
        List<WalletTransactionDto> list = walletService.getMyTransactions();
        return ResponseEntity.ok(list);
    }
}
