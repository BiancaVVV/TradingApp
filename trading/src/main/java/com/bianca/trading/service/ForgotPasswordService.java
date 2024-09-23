package com.bianca.trading.service;

import com.bianca.trading.domain.VerificationType;
import com.bianca.trading.modal.ForgotPasswordToken;
import com.bianca.trading.modal.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user,
                                    String id, String otp,
                                    VerificationType verificationType,
                                    String sendTo);

    ForgotPasswordToken findByToken(String id);

    ForgotPasswordToken findByUserId(long userId);

    void deleteToken(ForgotPasswordToken token);


    ForgotPasswordToken findById(String id);
}
