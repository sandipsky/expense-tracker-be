package com.sandipsky.expense_tracker.service;

import com.sandipsky.expense_tracker.repository.*;
import com.sandipsky.expense_tracker.entity.*;
import com.sandipsky.expense_tracker.exception.AccountLockException;
import com.sandipsky.expense_tracker.dto.login.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Value("${auth.maxFailedAttempts}")
        private int maxFailedAttempts;

        @Value("${auth.lockTimeDurationMs}")
        private long lockTimeDurationMs;

        public User authenticate(LoginRequest request) {
                User user = userRepository.findByUsername(request.getUsername())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                if (!user.getIsActive()) {
                        throw new RuntimeException("User account is inactive.");
                }

                if (!user.isAccountNonLocked()) {
                        if (user.getLockTime() != null) {
                                long lockTimeInMillis = user.getLockTime().getTime();
                                long currentTimeInMillis = System.currentTimeMillis();

                                if (currentTimeInMillis - lockTimeInMillis >= lockTimeDurationMs) {
                                        // Unlock the account
                                        user.setAccountNonLocked(true);
                                        user.setFailedAttempt(0);
                                        user.setLockTime(null);
                                        userRepository.save(user);
                                } else {
                                        throw new AccountLockException("Account is locked. Try again later.");
                                }
                        }
                        else {
                                throw new AccountLockException("Account is locked. Try again later.");
                        }
                }

                try {
                        authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        request.getUsername(),
                                                        request.getPassword()));
                } catch (Exception ex) {
                        int attempts = user.getFailedAttempt() + 1;
                        user.setFailedAttempt(attempts);

                        if (attempts >= maxFailedAttempts) {
                                user.setAccountNonLocked(false);
                                user.setLockTime(new java.util.Date());
                        }

                        userRepository.save(user);
                        throw new BadCredentialsException("Username or Password is Incorrect.");
                }

                user.setFailedAttempt(0);
                user.setAccountNonLocked(true);
                user.setLockTime(null);
                userRepository.save(user);
                return user;
        }
}
