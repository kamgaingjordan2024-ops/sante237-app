package com.sante237.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "patients")
public class Patient extends Utilisateur {

    private String adresse;
    private String dateNaissance;
}