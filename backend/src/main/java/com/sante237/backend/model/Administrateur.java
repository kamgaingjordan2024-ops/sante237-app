package com.sante237.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "administrateurs")
public class Administrateur extends Utilisateur {

}