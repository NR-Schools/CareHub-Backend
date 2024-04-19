package com.it120p.carehub.model.dto;

import com.it120p.carehub.model.entity.Offer;
import com.it120p.carehub.model.entity.OfferStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferDTO {
    private long offerId;
    private UserInfoDTO serviceProvider;
    private RequestDTO request;
    private String offerDetails;
    private OfferStatus offerStatus;

    public static OfferDTO fromOffer(Offer offer) {
        return OfferDTO.builder()
                .offerId(offer.getOfferId())
                .offerDetails(offer.getOfferDetails())
                .offerStatus(offer.getOfferStatus())
                .request(
                        RequestDTO.fromRequest(
                                offer.getRequest()
                        )
                )
                .serviceProvider(
                        UserInfoDTO.fromUser(
                                offer.getServiceProvider()
                        )
                )
                .build();
    }
}
