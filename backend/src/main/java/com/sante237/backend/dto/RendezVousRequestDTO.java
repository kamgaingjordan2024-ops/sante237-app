package com.sante237.backend.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Requête pour créer ou modifier un rendez-vous")
public class RendezVousRequestDTO {

    @NotNull(message = "La date et heure sont obligatoires")
    @Schema(description = "Date et heure du rendez-vous", example = "2026-06-01T10:00:00")
    private String dateHeure;

    @Schema(description = "Durée en minutes", example = "30")
    private int duree;

    @NotBlank(message = "Le statut est obligatoire")
    @Schema(description = "Statut du rendez-vous", example = "PLANIFIE")
    private String statut;

    @Schema(description = "ID du patient", example = "1")
    private Long patientId;

    @Schema(description = "ID du médecin", example = "1")
    private Long medecinId;

    @Schema(description = "ID du créneau", example = "1")
    private Long creneauId;

    public RendezVousRequestDTO() {}
    public String getDateHeure() { return dateHeure; }
    public void setDateHeure(String dateHeure) { this.dateHeure = dateHeure; }
    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getMedecinId() { return medecinId; }
    public void setMedecinId(Long medecinId) { this.medecinId = medecinId; }
    public Long getCreneauId() { return creneauId; }
    public void setCreneauId(Long creneauId) { this.creneauId = creneauId; }
}