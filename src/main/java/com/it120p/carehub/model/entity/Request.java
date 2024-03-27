package com.it120p.carehub.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long requestId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    private String requestDetails;

    @Enumerated(EnumType.STRING)
    private Status requestStatus;
}
