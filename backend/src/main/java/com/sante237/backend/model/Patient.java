package com.sante237.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "patients")
public class Patient extends Utilisateur {

    private String adresse;
    private String dateNaissance;
}