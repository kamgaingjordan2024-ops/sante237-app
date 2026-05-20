package com.sante237.backend.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Requête pour créer ou modifier une spécialité")
public class SpecialiteRequestDTO {
    @NotBlank(message = "Le nom est obligatoire")
    @Schema(description = "Nom de la spécialité", example = "Cardiologie")
    private String nom;
    public SpecialiteRequestDTO() {}
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}