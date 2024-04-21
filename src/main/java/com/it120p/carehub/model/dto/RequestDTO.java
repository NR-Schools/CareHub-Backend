package com.it120p.carehub.model.dto;

import java.util.List;

import com.it120p.carehub.model.entity.Request;
import com.it120p.carehub.model.entity.RequestStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestDTO {
    private long requestId;
    private UserInfoDTO customer;
    private String requestDetails;
    private RequestStatus requestStatus;
    private List<OfferDTO> offers;

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

    public static RequestDTO fromRequestWithOfferList(Request request) {
        return RequestDTO.builder()
                .requestId(request.getRequestId())
                .requestDetails(request.getRequestDetails())
                .requestStatus(request.getRequestStatus())
                .customer(
                        UserInfoDTO.fromUser(request.getCustomer())
                )
                .offers(
                    request.getOffers()
                        .stream()
                        .map(OfferDTO::fromOfferWithoutRequest)
                        .toList()
                )
                .build();
    }
}
