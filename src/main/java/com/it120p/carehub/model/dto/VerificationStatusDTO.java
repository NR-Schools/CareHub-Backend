package com.it120p.carehub.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationStatusDTO {
    private String email;
    private boolean isVerifySuccessful;
}
