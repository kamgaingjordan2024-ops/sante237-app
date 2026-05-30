package com.sante237.backend.service;

import com.sante237.backend.dto.NotificationRequestDTO;
import com.sante237.backend.dto.NotificationResponseDTO;
import java.util.List;

public interface INotificationService {

    /** Récupère la liste de toutes les notifications */
    List<NotificationResponseDTO> getAllNotifications();
    List<NotificationResponseDTO> getNotificationsByUtilisateur(Long utilisateurId);

    /** Récupère une notification par son ID */
    NotificationResponseDTO getNotificationById(Long id);

    /** Crée une nouvelle notification */
    NotificationResponseDTO createNotification(NotificationRequestDTO dto);

    /** Met à jour une notification existante */
    NotificationResponseDTO updateNotification(Long id, NotificationRequestDTO dto);

    /** Supprime une notification */
    boolean deleteNotification(Long id);
}