package com.sante237.backend.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse contenant les informations d'une notification")
public class NotificationResponseDTO {
    private Long id_notification;
    private String message;
    private String dateEnvoi;
    private boolean lue;
    private Long utilisateurId;
    private String utilisateurNom;

    public NotificationResponseDTO() {}
    public NotificationResponseDTO(Long id_notification, String message, String dateEnvoi,
                                    boolean lue, Long utilisateurId, String utilisateurNom) {
        this.id_notification = id_notification; this.message = message;
        this.dateEnvoi = dateEnvoi; this.lue = lue;
        this.utilisateurId = utilisateurId; this.utilisateurNom = utilisateurNom;
    }
    public Long getId_notification() { return id_notification; }
    public void setId_notification(Long id_notification) { this.id_notification = id_notification; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getDateEnvoi() { return dateEnvoi; }
    public void setDateEnvoi(String dateEnvoi) { this.dateEnvoi = dateEnvoi; }
    public boolean isLue() { return lue; }
    public void setLue(boolean lue) { this.lue = lue; }
    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }
    public String getUtilisateurNom() { return utilisateurNom; }
    public void setUtilisateurNom(String utilisateurNom) { this.utilisateurNom = utilisateurNom; }
}