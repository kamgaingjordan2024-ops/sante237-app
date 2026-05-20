package com.sante237.backend.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse contenant les informations d'un rendez-vous")
public class RendezVousResponseDTO {
    private Long id_rendezvous;
    private String dateHeure;
    private int duree;
    private String statut;
    private Long patientId;
    private String patientNom;
    private Long medecinId;
    private String medecinNom;
    private Long creneauId;

    public RendezVousResponseDTO() {}
    public RendezVousResponseDTO(Long id_rendezvous, String dateHeure, int duree, String statut,
                                  Long patientId, String patientNom, Long medecinId, String medecinNom, Long creneauId) {
        this.id_rendezvous = id_rendezvous; this.dateHeure = dateHeure; this.duree = duree;
        this.statut = statut; this.patientId = patientId; this.patientNom = patientNom;
        this.medecinId = medecinId; this.medecinNom = medecinNom; this.creneauId = creneauId;
    }
    public Long getId_rendezvous() { return id_rendezvous; }
    public void setId_rendezvous(Long id_rendezvous) { this.id_rendezvous = id_rendezvous; }
    public String getDateHeure() { return dateHeure; }
    public void setDateHeure(String dateHeure) { this.dateHeure = dateHeure; }
    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public String getPatientNom() { return patientNom; }
    public void setPatientNom(String patientNom) { this.patientNom = patientNom; }
    public Long getMedecinId() { return medecinId; }
    public void setMedecinId(Long medecinId) { this.medecinId = medecinId; }
    public String getMedecinNom() { return medecinNom; }
    public void setMedecinNom(String medecinNom) { this.medecinNom = medecinNom; }
    public Long getCreneauId() { return creneauId; }
    public void setCreneauId(Long creneauId) { this.creneauId = creneauId; }
}