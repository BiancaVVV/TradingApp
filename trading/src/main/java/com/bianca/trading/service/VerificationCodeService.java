package com.bianca.trading.service;

import com.bianca.trading.domain.VerificationType;
import com.bianca.trading.modal.User;
import com.bianca.trading.modal.VerificationCode;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
