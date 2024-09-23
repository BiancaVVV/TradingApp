package com.bianca.trading.service;

import com.bianca.trading.domain.VerificationType;
import com.bianca.trading.modal.ForgotPasswordToken;
import com.bianca.trading.modal.User;
import com.bianca.trading.repository.ForgotPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordImpl implements ForgotPasswordService {


    @Autowired
    private ForgotPasswordRepository passwordRepository;
    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Override
    public ForgotPasswordToken createToken(User user,
                                           String id,
                                           String otp,
                                           VerificationType verificationType, String sendTo) {

        ForgotPasswordToken token=new ForgotPasswordToken();
        token.setUser(user);
        token.setSendTo(sendTo);
        token.setVerificationType(verificationType);
        token.setOtp(otp);
        token.setId(id);
        return forgotPasswordRepository.save(token);

    }

    @Override
    public ForgotPasswordToken findByToken(String id) {

        Optional<ForgotPasswordToken> token=forgotPasswordRepository.findById(id);
        return token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUserId(long userId) {
        return forgotPasswordRepository.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {

        forgotPasswordRepository.delete(token);
    }
}
