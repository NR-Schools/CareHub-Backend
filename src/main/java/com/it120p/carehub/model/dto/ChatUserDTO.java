package com.it120p.carehub.model.dto;

import com.it120p.carehub.model.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatUserDTO {
    private String name;
    private String email;
    private String photoId;

    public static ChatUserDTO fromUser(User user) {
        return ChatUserDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .photoId(user.getPhotoResource() != null ? user.getPhotoResource().getResourceId() : "")
                .build();
    }
}
