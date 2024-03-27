package com.it120p.carehub.model.entity;

import jakarta.persistence.*;

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
    private Status offerStatus;
}
