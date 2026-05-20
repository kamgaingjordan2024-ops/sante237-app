package com.sante237.backend.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse contenant les informations d'une spécialité")
public class SpecialiteResponseDTO {
    @Schema(description = "ID de la spécialité", example = "1")
    private Long id_specialite;
    @Schema(description = "Nom de la spécialité", example = "Cardiologie")
    private String nom;
    public SpecialiteResponseDTO() {}
    public SpecialiteResponseDTO(Long id_specialite, String nom) {
        this.id_specialite = id_specialite;
        this.nom = nom;
    }
    public Long getId_specialite() { return id_specialite; }
    public void setId_specialite(Long id_specialite) { this.id_specialite = id_specialite; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}