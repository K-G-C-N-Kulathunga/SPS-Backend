package com.it.sps.controller;

import com.it.sps.entity.ApplicationOtherAccount;
import com.it.sps.repository.ApplicationOtherAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class ApplicationOtherAccountController {

    @Autowired
    private ApplicationOtherAccountRepository accountRepository;

        // ✅ Get accounts by ID (NIC)
        @GetMapping("/{id}")
        public List<ApplicationOtherAccount> getAccounts(@PathVariable String id) {
            return accountRepository.findById(id);
        }

        // ✅ Save or Replace accounts
        @PostMapping("/{id}")
        @Transactional
        public void saveAccounts(@PathVariable String id, @RequestBody List<String> accountNumbers) {
            accountRepository.deleteById(id); // Remove previous
            accountNumbers.stream()
                    .filter(acc -> acc != null && !acc.trim().isEmpty())
                    .distinct()
                    .limit(4)
                    .forEach(acc -> accountRepository.save(new ApplicationOtherAccount(id, acc.trim())));
        }

    // ✅ PUT: Update (replace) accounts for the given ID
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> updateAccounts(@PathVariable String id, @RequestBody List<String> accountNumbers) {
        if (accountNumbers == null || accountNumbers.isEmpty()) {
            return ResponseEntity.badRequest().body("Account number list is empty.");
        }

        accountRepository.deleteById(id); // Remove previous
        saveAccountsToDB(id, accountNumbers);
        return ResponseEntity.ok("Accounts updated via PUT.");
    }

    // 🔁 Common helper for saving accounts
    private void saveAccountsToDB(String id, List<String> accountNumbers) {
        accountNumbers.stream()
                .filter(acc -> acc != null && !acc.trim().isEmpty())
                .distinct()
                .limit(4)
                .forEach(acc -> accountRepository.save(new ApplicationOtherAccount(id, acc.trim())));
    }
}
