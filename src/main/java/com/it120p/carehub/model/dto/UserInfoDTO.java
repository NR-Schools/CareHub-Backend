package com.it120p.carehub.model.dto;

import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.UserServiceCare;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserInfoDTO {
    private String email;
    private String name;
    private String contactNo;
    private LocalDate birthDate;
    private byte[] photoBytes;
    private UserServiceCare userServiceCare;

    public static UserInfoDTO fromUser(User user) {
        return UserInfoDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .contactNo(user.getContactNo())
                .birthDate(user.getBirthDate())
                .photoBytes(user.getPhotoBytes())
                .userServiceCare(user.getUserServiceCare())
                .build();
    }
}
