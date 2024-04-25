package com.it120p.carehub.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long requestId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private User customer;

    private String requestTitle;
    @Column(length = 5000)
    private String requestDetails;
    private String requestLocation;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "request")
    private List<Offer> offers;

    @CreatedDate
    private LocalDateTime createdDate;
}
