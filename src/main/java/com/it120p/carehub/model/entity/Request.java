package com.it120p.carehub.model.entity;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long requestId;

    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id")
    private User customer;

    private String requestDetails;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Offer> offers;
}
