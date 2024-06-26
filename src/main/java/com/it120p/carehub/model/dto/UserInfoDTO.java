package com.it120p.carehub.model.dto;

import com.it120p.carehub.model.entity.User;
import com.it120p.carehub.model.entity.UserServiceCare;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserInfoDTO {
    private String email;
    private String name;
    private String contactNo;
    private LocalDate birthDate;
    private String photoId;
    private UserServiceCare userServiceCare;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public static UserInfoDTO fromUser(User user) {
        return UserInfoDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .contactNo(user.getContactNo())
                .birthDate(user.getBirthDate())
                .photoId(
                        user.getPhotoResource() != null ? user.getPhotoResource().getResourceId() : ""
                )
                .userServiceCare(user.getUserServiceCare())
                .createdDate(user.getCreatedDate())
                .lastModifiedDate(user.getLastModifiedDate())
                .build();
    }
}
