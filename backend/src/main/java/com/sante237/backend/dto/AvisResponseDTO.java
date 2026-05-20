package com.sante237.backend.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse contenant les informations d'un avis")
public class AvisResponseDTO {
    private Long id_avis;
    private String commentaire;
    private int note;
    private Long patientId;
    private String patientNom;
    private Long rendezVousId;

    public AvisResponseDTO() {}
    public AvisResponseDTO(Long id_avis, String commentaire, int note,
                            Long patientId, String patientNom, Long rendezVousId) {
        this.id_avis = id_avis; this.commentaire = commentaire; this.note = note;
        this.patientId = patientId; this.patientNom = patientNom; this.rendezVousId = rendezVousId;
    }
    public Long getId_avis() { return id_avis; }
    public void setId_avis(Long id_avis) { this.id_avis = id_avis; }
    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    public int getNote() { return note; }
    public void setNote(int note) { this.note = note; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public String getPatientNom() { return patientNom; }
    public void setPatientNom(String patientNom) { this.patientNom = patientNom; }
    public Long getRendezVousId() { return rendezVousId; }
    public void setRendezVousId(Long rendezVousId) { this.rendezVousId = rendezVousId; }
}