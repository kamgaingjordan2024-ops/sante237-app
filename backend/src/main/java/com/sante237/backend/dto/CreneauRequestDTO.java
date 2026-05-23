package com.sante237.backend.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Schema(description = "Requête pour créer ou modifier un créneau")
public class CreneauRequestDTO {
    @NotNull(message = "La date et heure sont obligatoires")
    @Schema(description = "Date et heure du créneau", example = "2026-06-01T09:00:00")
    private String dateHeure;

    @Min(value = 1, message = "La durée doit être supérieure à 0")
    @Schema(description = "Durée en minutes", example = "30")
    private int duree;

    @Schema(description = "Disponibilité du créneau", example = "true")
    private boolean disponible;

    @Schema(description = "ID du médecin", example = "1")
    private Long medecinId;

    @Schema(description = "ID de l'hôpital", example = "1")
    private Long hopitalId;

    public CreneauRequestDTO() {}
    public String getDateHeure() { return dateHeure; }
    public void setDateHeure(String dateHeure) { this.dateHeure = dateHeure; }
    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public Long getMedecinId() { return medecinId; }
    public void setMedecinId(Long medecinId) { this.medecinId = medecinId; }
    public Long getHopitalId() { return hopitalId; }
    public void setHopitalId(Long hopitalId) { this.hopitalId = hopitalId; }
}