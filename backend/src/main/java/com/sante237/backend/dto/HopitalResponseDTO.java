package com.sante237.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse contenant les informations d'un hôpital")
public class HopitalResponseDTO {

    @Schema(description = "ID de l'hôpital", example = "1")
    private Long id_hopital;

    @Schema(description = "Nom de l'hôpital", example = "Hôpital Provincial de Dschang")
    private String nom;

    @Schema(description = "Adresse de l'hôpital", example = "Boulevard de la Liberté, Dschang")
    private String adresse;

    @Schema(description = "Numéro de téléphone", example = "237677123456")
    private String telephone;

    // Constructeurs
    public HopitalResponseDTO() {}

    public HopitalResponseDTO(Long id_hopital, String nom, String adresse, String telephone) {
        this.id_hopital = id_hopital;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    // Getters et Setters
    public Long getId_hopital() {
        return id_hopital;
    }

    public void setId_hopital(Long id_hopital) {
        this.id_hopital = id_hopital;
    }

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

