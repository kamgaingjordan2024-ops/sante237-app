package com.sante237.backend.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse contenant les informations d'un créneau")
public class CreneauResponseDTO {
    private Long id_creneau;
    private String dateHeure;
    private int duree;
    private boolean disponible;
    private Long medecinId;
    private String medecinNom;
    private Long hopitalId;
    private String hopitalNom;

    public CreneauResponseDTO() {}
    public CreneauResponseDTO(Long id_creneau, String dateHeure, int duree, boolean disponible,
                               Long medecinId, String medecinNom, Long hopitalId, String hopitalNom) {
        this.id_creneau = id_creneau; this.dateHeure = dateHeure; this.duree = duree;
        this.disponible = disponible; this.medecinId = medecinId; this.medecinNom = medecinNom;
        this.hopitalId = hopitalId; this.hopitalNom = hopitalNom;
    }
    public Long getId_creneau() { return id_creneau; }
    public void setId_creneau(Long id_creneau) { this.id_creneau = id_creneau; }
    public String getDateHeure() { return dateHeure; }
    public void setDateHeure(String dateHeure) { this.dateHeure = dateHeure; }
    public int getDuree() { return duree; }
    public void setDuree(int duree) { this.duree = duree; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public Long getMedecinId() { return medecinId; }
    public void setMedecinId(Long medecinId) { this.medecinId = medecinId; }
    public String getMedecinNom() { return medecinNom; }
    public void setMedecinNom(String medecinNom) { this.medecinNom = medecinNom; }
    public Long getHopitalId() { return hopitalId; }
    public void setHopitalId(Long hopitalId) { this.hopitalId = hopitalId; }
    public String getHopitalNom() { return hopitalNom; }
    public void setHopitalNom(String hopitalNom) { this.hopitalNom = hopitalNom; }
}