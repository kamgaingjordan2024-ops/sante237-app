package com.sante237.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Requête pour créer ou modifier un hôpital")
public class HopitalRequestDTO {

    @NotBlank(message = "Le nom de l'hôpital est obligatoire")
    @Schema(description = "Nom de l'hôpital", example = "Hôpital Provincial de Dschang")
    private String nom;

    @NotBlank(message = "L'adresse est obligatoire")
    @Schema(description = "Adresse de l'hôpital", example = "Boulevard de la Liberté, Dschang")
    private String adresse;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^[0-9]{9,15}$", message = "Numéro de téléphone invalide")
    @Schema(description = "Numéro de téléphone", example = "237677123456")
    private String telephone;

    // Constructeurs
    public HopitalRequestDTO() {}

    public HopitalRequestDTO(String nom, String adresse, String telephone) {
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}

