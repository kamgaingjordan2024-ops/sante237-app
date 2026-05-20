package com.sante237.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Requête pour créer ou modifier un patient")
public class PatientRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    @Schema(description = "Nom du patient", example = "Kamga")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Schema(description = "Prénom du patient", example = "Paul")
    private String prenom;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^[0-9]{9,15}$", message = "Numéro de téléphone invalide")
    @Schema(description = "Téléphone", example = "237677123456")
    private String telephone;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    @Schema(description = "Email", example = "paul.kamga@gmail.com")
    private String email;

    @NotBlank(message = "L'adresse est obligatoire")
    @Schema(description = "Adresse du patient", example = "Quartier Bafoussam")
    private String adresse;

    @Schema(description = "Date de naissance", example = "1990-05-15")
    private String dateNaissance;

    public PatientRequestDTO() {}

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