package com.sante237.backend.repository;

import com.sante237.backend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
List<Notification> findByUtilisateur_IdUtilisateur(Long utilisateurId);
}