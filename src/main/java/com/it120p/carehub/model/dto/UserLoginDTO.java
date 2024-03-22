package com.it120p.carehub.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDTO {
    private String email;
    private String token;
}
