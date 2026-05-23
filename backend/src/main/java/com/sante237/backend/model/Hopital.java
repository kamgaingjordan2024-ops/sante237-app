package com.sante237.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "hopitaux")
public class Hopital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Idhopital;

    @Column(nullable = false, unique = true)
    private String nom;
    private String adresse;
    private String telephone;
}