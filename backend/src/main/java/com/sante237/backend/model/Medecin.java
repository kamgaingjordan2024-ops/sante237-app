package com.sante237.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "medecins")
public class Medecin extends Utilisateur {

    private String matricule;

    @ManyToOne
    @JoinColumn(name = "hopital_id")
    private Hopital hopital;

    @ManyToMany
    @JoinTable(name = "medecin_specialite",
        joinColumns = @JoinColumn(name = "medecin_id"),
        inverseJoinColumns = @JoinColumn(name = "specialite_id"))
    private List<Specialite> specialites;
}