package com.it120p.carehub.model.dto;

import com.it120p.carehub.model.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserRegisterDTO {
    private String email;
    private String name;
    private String contactNo;
    private LocalDate birthDate;

    public static UserRegisterDTO fromUser(User user) {
        return UserRegisterDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .contactNo(user.getContactNo())
                .birthDate(user.getBirthDate())
                .build();
    }
}
