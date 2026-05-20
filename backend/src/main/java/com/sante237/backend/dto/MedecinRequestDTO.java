package com.sante237.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import java.util.List;

@Schema(description = "Requête pour créer ou modifier un médecin")
public class MedecinRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    @Schema(description = "Nom du médecin", example = "Dupont")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Schema(description = "Prénom du médecin", example = "Jean")
    private String prenom;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "^[0-9]{9,15}$", message = "Numéro de téléphone invalide")
    @Schema(description = "Téléphone", example = "237677123456")
    private String telephone;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    @Schema(description = "Email", example = "jean.dupont@sante237.com")
    private String email;

    @NotBlank(message = "La matricule est obligatoire")
    @Schema(description = "Matricule du médecin", example = "MED-001")
    private String matricule;

    @Schema(description = "ID de l'hôpital", example = "1")
    private Long hopitalId;

    @Schema(description = "Liste des IDs des spécialités")
    private List<Long> specialiteIds;

    public MedecinRequestDTO() {}

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
    public List<Long> getSpecialiteIds() { return specialiteIds; }
    public void setSpecialiteIds(List<Long> specialiteIds) { this.specialiteIds = specialiteIds; }
}