package com.sante237.backend.controller;
import com.sante237.backend.dto.NotificationRequestDTO;
import com.sante237.backend.dto.NotificationResponseDTO;
import com.sante237.backend.service.INotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
@Tag(name = "Notifications", description = "Gestion complète des notifications")
public class NotificationController {

    @Autowired private INotificationService notificationService;

    @GetMapping
    @Operation(summary = "Lister toutes les notifications")
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une notification par ID")
    @ApiResponse(responseCode = "200", description = "Notification trouvée")
    @ApiResponse(responseCode = "404", description = "Notification non trouvée")
    public ResponseEntity<NotificationResponseDTO> getNotificationById(
        @Parameter(description = "ID de la notification", example = "1") @PathVariable Long id) {
        return new ResponseEntity<>(notificationService.getNotificationById(id), HttpStatus.OK);
    }
    @GetMapping("/utilisateur/{utilisateurId}")
@Operation(summary = "Lister les notifications d'un utilisateur")
@ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUtilisateur(
    @Parameter(description = "ID de l'utilisateur", example = "1") @PathVariable Long utilisateurId) {
    return ResponseEntity.ok(notificationService.getNotificationsByUtilisateur(utilisateurId));
}

    @PostMapping
    @Operation(summary = "Créer une notification")
    @ApiResponse(responseCode = "201", description = "Notification créée avec succès")
    public ResponseEntity<NotificationResponseDTO> createNotification(@Valid @RequestBody NotificationRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.createNotification(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une notification")
    @ApiResponse(responseCode = "200", description = "Notification mise à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Notification non trouvée")
    public ResponseEntity<NotificationResponseDTO> updateNotification(
        @Parameter(description = "ID de la notification", example = "1") @PathVariable Long id,
        @Valid @RequestBody NotificationRequestDTO dto) {
        try {
            return ResponseEntity.ok(notificationService.updateNotification(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une notification")
    @ApiResponse(responseCode = "204", description = "Notification supprimée avec succès")
    @ApiResponse(responseCode = "404", description = "Notification non trouvée")
    public ResponseEntity<Void> deleteNotification(
        @Parameter(description = "ID de la notification", example = "1") @PathVariable Long id) {
        return notificationService.deleteNotification(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}

