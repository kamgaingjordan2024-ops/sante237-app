package com.sante237.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse contenant les informations d'un patient")
public class PatientResponseDTO {

    @Schema(description = "ID du patient", example = "1")
    private Long id_utilisateur;

    @Schema(description = "Nom du patient", example = "Kamga")
    private String nom;

    @Schema(description = "Prénom du patient", example = "Paul")
    private String prenom;

    @Schema(description = "Téléphone", example = "237677123456")
    private String telephone;

    @Schema(description = "Email", example = "paul.kamga@gmail.com")
    private String email;

    @Schema(description = "Adresse", example = "Quartier Bafoussam")
    private String adresse;

    @Schema(description = "Date de naissance", example = "1990-05-15")
    private String dateNaissance;

    public PatientResponseDTO() {}

    public PatientResponseDTO(Long id_utilisateur, String nom, String prenom, String telephone,
                               String email, String adresse, String dateNaissance) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
    }

    public Long getId_utilisateur() { return id_utilisateur; }
    public void setId_utilisateur(Long id_utilisateur) { this.id_utilisateur = id_utilisateur; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }
}