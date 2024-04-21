package com.it120p.carehub.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

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

    private String requestTitle;
    private String requestDetails;
    private String requestLocation;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Offer> offers;

    @CreatedDate
    private LocalDateTime createdDate;
}
