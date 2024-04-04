package com.it120p.carehub.model.dto;

import com.it120p.carehub.model.entity.Request;
import com.it120p.carehub.model.entity.RequestStatus;
import com.it120p.carehub.model.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestDTO {
    private long requestId;
    private UserInfoDTO customer;
    private String requestDetails;
    private RequestStatus requestStatus;

    public static RequestDTO fromRequest(Request request) {
        return RequestDTO.builder()
                .requestId(request.getRequestId())
                .requestDetails(request.getRequestDetails())
                .requestStatus(request.getRequestStatus())
                .customer(
                        UserInfoDTO.fromUser(request.getCustomer())
                )
                .build();
    }
}
