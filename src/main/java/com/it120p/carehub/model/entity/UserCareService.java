package com.it120p.carehub.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_care_services")
public class UserCareService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String type;
    private String description;
    private String location;

    @ElementCollection
    private List<String> offerings;
}
