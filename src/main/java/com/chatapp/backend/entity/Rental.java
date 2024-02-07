package com.chatapp.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surface")
    private Integer surface;

    @Column(name = "price")
    private Integer price;

    @Column(name = "picture")
    private String picture;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false, referencedColumnName = "id")
    private User owner_id;

}

