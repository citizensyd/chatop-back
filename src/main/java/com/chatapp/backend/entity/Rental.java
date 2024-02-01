package com.chatapp.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_rental")
public class Rental {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surface")
    private Integer surface;

    @Column(name = "price")
    private Integer price;

    @Column(name = "picture", length = 1000000)
    @Lob
    private byte[] picture;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false, referencedColumnName = "id")
    private User owner;

}

