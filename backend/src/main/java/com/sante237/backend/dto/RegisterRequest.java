package com.sante237.backend.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Requête d'inscription")
public class RegisterRequest {

    @NotBlank(message = "Le nom est obligatoire")
    @Schema(description = "Nom de l'utilisateur", example = "Dupont")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Schema(description = "Prénom de l'utilisateur", example = "Jean")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    @Schema(description = "Email de l'utilisateur", example = "jean.dupont@example.com")
    private String email;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^[0-9]{9,15}$", message = "Numéro de téléphone invalide")
    @Schema(description = "Numéro de téléphone", example = "243971234567")
    private String telephone;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Schema(description = "Mot de passe (minimum 8 caractères)", example = "password123")
    private String motDePasse;

    @NotBlank(message = "Le rôle est obligatoire")
    @Pattern(regexp = "PATIENT|MEDECIN|ADMIN", message = "Rôle invalide")
    @Schema(description = "Rôle de l'utilisateur", example = "PATIENT", allowableValues = {"PATIENT", "MEDECIN", "ADMIN"})
    private String role;

    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}