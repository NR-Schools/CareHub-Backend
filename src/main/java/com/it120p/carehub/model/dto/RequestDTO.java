package com.it120p.carehub.model.dto;

import java.time.LocalDateTime;
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
    private String requestTitle;
    private String requestDetails;
    private String requestLocation;
    private RequestStatus requestStatus;
    private LocalDateTime requestDateCreated;
    private List<OfferDTO> offers;

    public static RequestDTO fromRequest(Request request) {
        return RequestDTO.builder()
                .requestId(request.getRequestId())
                .requestTitle(request.getRequestTitle())
                .requestDetails(request.getRequestDetails())
                .requestLocation(request.getRequestLocation())
                .requestStatus(request.getRequestStatus())
                .customer(
                        UserInfoDTO.fromUser(request.getCustomer())
                )
                .requestDateCreated(request.getCreatedDate())
                .build();
    }

    public static RequestDTO fromRequestWithOfferList(Request request) {
        return RequestDTO.builder()
                .requestId(request.getRequestId())
                .requestTitle(request.getRequestTitle())
                .requestDetails(request.getRequestDetails())
                .requestLocation(request.getRequestLocation())
                .requestStatus(request.getRequestStatus())
                .customer(
                        UserInfoDTO.fromUser(request.getCustomer())
                )
                .requestDateCreated(request.getCreatedDate())
                .offers(
                    request.getOffers()
                        .stream()
                        .map(OfferDTO::fromOfferWithoutRequest)
                        .toList()
                )
                .build();
    }
}
