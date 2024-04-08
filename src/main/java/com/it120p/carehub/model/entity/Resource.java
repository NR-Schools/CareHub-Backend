package com.it120p.carehub.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "resources")
public class Resource {

    @Id
    private String resourceId;

    private String resourceType;

    private String contentType;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] resourceBytes;
}
