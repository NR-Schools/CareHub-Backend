package com.it120p.carehub.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long offerId;

    @ManyToOne
    @JoinColumn(name = "service_provider_id")
    private User serviceProvider;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    private String offerDetails;

    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;
}
