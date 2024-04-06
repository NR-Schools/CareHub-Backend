package com.it120p.carehub.controller;

import com.it120p.carehub.model.dto.OtpRequest;
import com.it120p.carehub.model.dto.OtpResponseDto;
import com.it120p.carehub.model.dto.OtpValidationRequest;
import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest) {
        log.info("inside sendOtp :: "+otpRequest.getEmail());
        return smsService.sendSMS(otpRequest);
    }

    @PostMapping("/validate-otp")
    public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
        log.info("inside validateOtp :: "+otpValidationRequest.getEmail()+" "+otpValidationRequest.getOtpNumber());
        return smsService.validateOtp(otpValidationRequest);
    }

}
