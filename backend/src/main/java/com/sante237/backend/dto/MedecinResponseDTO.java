package com.sante237.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Réponse contenant les informations d'un médecin")
public class MedecinResponseDTO {

    @Schema(description = "ID du médecin", example = "1")
    private Long id_utilisateur;

    @Schema(description = "Nom du médecin", example = "Dupont")
    private String nom;

    @Schema(description = "Prénom du médecin", example = "Jean")
    private String prenom;

    @Schema(description = "Téléphone", example = "237677123456")
    private String telephone;

    @Schema(description = "Email", example = "jean.dupont@sante237.com")
    private String email;

    @Schema(description = "Matricule", example = "MED-001")
    private String matricule;

    @Schema(description = "ID de l'hôpital", example = "1")
    private Long hopitalId;

    @Schema(description = "Nom de l'hôpital", example = "Hôpital Provincial de Dschang")
    private String hopitalNom;

    @Schema(description = "Liste des spécialités")
    private List<String> specialites;

    public MedecinResponseDTO() {}

    public MedecinResponseDTO(Long id_utilisateur, String nom, String prenom, String telephone,
                               String email, String matricule, Long hopitalId, String hopitalNom,
                               List<String> specialites) {
        this.id_utilisateur = id_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.matricule = matricule;
        this.hopitalId = hopitalId;
        this.hopitalNom = hopitalNom;
        this.specialites = specialites;
    }

    public Long getId_utilisateur() { return id_utilisateur; }
    public void setId_utilisateur(Long id_utilisateur) { this.id_utilisateur = id_utilisateur; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public Long getHopitalId() { return hopitalId; }
    public void setHopitalId(Long hopitalId) { this.hopitalId = hopitalId; }
    public String getHopitalNom() { return hopitalNom; }
    public void setHopitalNom(String hopitalNom) { this.hopitalNom = hopitalNom; }
    public List<String> getSpecialites() { return specialites; }
    public void setSpecialites(List<String> specialites) { this.specialites = specialites; }
}