package com.sante237.backend.service;

import com.sante237.backend.dto.NotificationRequestDTO;
import com.sante237.backend.dto.NotificationResponseDTO;
import com.sante237.backend.model.Notification;
import com.sante237.backend.model.Utilisateur;
import com.sante237.backend.repository.NotificationRepository;
import com.sante237.backend.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private NotificationResponseDTO convertToResponseDTO(Notification n) {
        return new NotificationResponseDTO(
            n.getId_notification(),
            n.getMessage(),
            n.getDateEnvoi() != null ? n.getDateEnvoi().toString() : null,
            n.isLue(),
            n.getUtilisateur() != null ? n.getUtilisateur().getIdUtilisateur() : null,
            n.getUtilisateur() != null ? n.getUtilisateur().getNom() + " " + n.getUtilisateur().getPrenom() : null
        );
    }

    @Override
    public List<NotificationResponseDTO> getAllNotifications() {
        return notificationRepository.findAll()
            .stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDTO getNotificationById(Long id) {
        return notificationRepository.findById(id)
            .map(this::convertToResponseDTO)
            .orElseThrow(() -> new EntityNotFoundException("Notification non trouvée avec l'ID: " + id));
    }

    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO dto) {
        Notification n = new Notification();
        n.setMessage(dto.getMessage());
        n.setLue(dto.isLue());
        n.setDateEnvoi(LocalDateTime.now());
        if (dto.getUtilisateurId() != null) {
            Utilisateur u = utilisateurRepository.findById(dto.getUtilisateurId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
            n.setUtilisateur(u);
        }
        return convertToResponseDTO(notificationRepository.save(n));
    }

    @Override
    public NotificationResponseDTO updateNotification(Long id, NotificationRequestDTO dto) {
        Optional<Notification> notif = notificationRepository.findById(id);
        if (notif.isPresent()) {
            Notification existing = notif.get();
            existing.setMessage(dto.getMessage());
            existing.setLue(dto.isLue());
            if (dto.getUtilisateurId() != null) {
                Utilisateur u = utilisateurRepository.findById(dto.getUtilisateurId())
                    .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
                existing.setUtilisateur(u);
            }
            return convertToResponseDTO(notificationRepository.save(existing));
        }
        throw new RuntimeException("Notification non trouvée avec l'ID: " + id);
    }

    @Override
    public boolean deleteNotification(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}