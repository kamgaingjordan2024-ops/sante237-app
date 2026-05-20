package com.sante237.backend.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Schema(description = "Requête pour créer ou modifier un avis")
public class AvisRequestDTO {
    @NotBlank(message = "Le commentaire est obligatoire")
    @Schema(description = "Commentaire de l'avis", example = "Très bon médecin")
    private String commentaire;

    @Min(value = 1, message = "La note minimale est 1")
    @Max(value = 5, message = "La note maximale est 5")
    @Schema(description = "Note de 1 à 5", example = "5")
    private int note;

    @Schema(description = "ID du patient", example = "1")
    private Long patientId;

    @Schema(description = "ID du rendez-vous", example = "1")
    private Long rendezVousId;

    public AvisRequestDTO() {}
    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    public int getNote() { return note; }
    public void setNote(int note) { this.note = note; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getRendezVousId() { return rendezVousId; }
    public void setRendezVousId(Long rendezVousId) { this.rendezVousId = rendezVousId; }
}