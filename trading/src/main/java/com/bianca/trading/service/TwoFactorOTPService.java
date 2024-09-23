package com.bianca.trading.service;

import com.bianca.trading.modal.TwoFactorOTP;
import com.bianca.trading.modal.User;

public interface TwoFactorOTPService {

    TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);

    TwoFactorOTP findByUser(Long userId);

    TwoFactorOTP findById(String id);

    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp);

    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp);
}


