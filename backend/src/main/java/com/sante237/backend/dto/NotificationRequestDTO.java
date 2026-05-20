package com.sante237.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Requête pour créer ou modifier une notification")
public class NotificationRequestDTO {

    @NotBlank(message = "Le message est obligatoire")
    @Schema(description = "Message", example = "Votre rendez-vous est confirmé")
    private String message;

    @Schema(description = "Statut de lecture", example = "false")
    private boolean lue;

    @NotNull(message = "L'ID utilisateur est obligatoire")
    @Schema(description = "ID de l'utilisateur", example = "1")
    private Long utilisateurId;

    public NotificationRequestDTO() {}

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public boolean isLue() { return lue; }
    public void setLue(boolean lue) { this.lue = lue; }
    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }
}