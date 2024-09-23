package com.bianca.trading.controller;

import com.bianca.trading.request.ForgotPasswordTokenRequest;
import com.bianca.trading.domain.VerificationType;
import com.bianca.trading.modal.ForgotPasswordToken;
import com.bianca.trading.modal.User;
import com.bianca.trading.modal.VerificationCode;
import com.bianca.trading.request.ResetPasswordRequest;
import com.bianca.trading.response.ApiResponse;
import com.bianca.trading.response.AuthResponse;
import com.bianca.trading.service.EmailService;
import com.bianca.trading.service.ForgotPasswordService;
import com.bianca.trading.service.UserService;
import com.bianca.trading.service.VerificationCodeService;
import com.bianca.trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private EmailService emailService;
    private String jwt;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable VerificationType verificationType) throws Exception {


        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUser(user.getId());

        if (verificationCode == null) {

            verificationCode=verificationCodeService.sendVerificationCode(user,verificationType);


        }

        if(verificationType.equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }



        return new ResponseEntity<>("verification otp send successfully", HttpStatus.OK);
    }


    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
            @PathVariable String otp,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        String sendTo=verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
                verificationCode.getEmail():verificationCode.getMobile();

     boolean isVerified=verificationCode.getOtp().equals(otp);

     if(isVerified){
         User updatedUser=userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(),sendTo,user);

         verificationCodeService.deleteVerificationCodeById(verificationCode);
         return new ResponseEntity<>(updatedUser, HttpStatus.OK);
     }

        throw new Exception("wrong otp");
    }


    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
            @RequestHeader("Authorization") String jwt,
            @RequestBody ForgotPasswordTokenRequest req) throws Exception {

        User user = userService.findUserByEmail(req.getSendTo());
        String otp = OtpUtils.generateOTP();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken token = forgotPasswordService.findByUserId(user.getId());

        if(token==null){
            token = forgotPasswordService.createToken(user,id,otp,req.getVerificationType(), req.getSendTo() );
        }

        if(req.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(),token.getOtp());
        }


        AuthResponse response=new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Password reset successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestParam String id,
            @RequestBody ResetPasswordRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {



        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);


        boolean isVerified=forgotPasswordToken.getOtp().equals(req.getOtp());
        if(isVerified){
            userService.updatePassword(forgotPasswordToken.getUser(),req.getPassword());
            ApiResponse res = new ApiResponse();
            res.setMessage("Password update successfully");
            return new ResponseEntity<>(res,HttpStatus.ACCEPTED);

        }
        throw new Exception("wrong otp");
    }





}
