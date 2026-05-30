package com.sante237.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "creneaux")
public class Creneau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCreneau;

    private LocalDateTime dateHeure;
    private int duree;
    private boolean disponible;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;

    @ManyToOne
    @JoinColumn(name = "hopital_id")
    private Hopital hopital;
}