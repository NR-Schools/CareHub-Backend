package com.it120p.carehub.controller;

import com.it120p.carehub.model.dto.OtpResponseDto;
import com.it120p.carehub.model.dto.OtpValidationRequest;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
@Slf4j

public class OtpController {

    @Autowired
    private SmsService smsService;

    @GetMapping("/process")
    public String processSMS() {
        return "SMS sent";
    }

    @PostMapping("/send-otp")
    public OtpResponseDto sendOtp(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String email = user.getEmail();
        String contactNo = user.getContactNo();
        log.info("inside sendOtp :: "+email);
        return smsService.sendSMS(email, contactNo);
    }

    @PostMapping("/validate-otp")
    public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
        log.info("inside validateOtp :: "+otpValidationRequest.getEmail()+" "+otpValidationRequest.getOtpNumber());
        return smsService.validateOtp(otpValidationRequest);
    }

}
