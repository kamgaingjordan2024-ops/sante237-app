package com.sante237.backend.controller;

import com.sante237.backend.model.Notification;
import com.sante237.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping
    public List<Notification> getAll() { return notificationRepository.findAll(); }

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        return notificationRepository.save(notification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        notificationRepository.deleteById(id);
        return ResponseEntity.ok("Supprimé");
    }
}