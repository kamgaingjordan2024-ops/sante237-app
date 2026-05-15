package com.sante237.backend.controller;

import jakarta.validation.Valid;
import com.sante237.backend.dto.LoginRequest;
import com.sante237.backend.dto.RegisterRequest;
import com.sante237.backend.model.*;
import com.sante237.backend.repository.UtilisateurRepository;
import com.sante237.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid@RequestBody RegisterRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email déjà utilisé !");
        }

        Utilisateur user;
        switch (request.getRole().toUpperCase()) {
            case "PATIENT" -> user = new Patient();
            case "MEDECIN" -> user = new Medecin();
            case "ADMINISTRATEUR" -> user = new Administrateur();
            default -> { return ResponseEntity.badRequest().body("Rôle invalide !"); }
        }

        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setTelephone(request.getTelephone());
        user.setRole(request.getRole().toUpperCase());
        user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));

        utilisateurRepository.save(user);
        return ResponseEntity.ok("Inscription réussie !");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return utilisateurRepository.findByEmail(request.getEmail())
                .map(user -> {
                    if (passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())) {
                        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
                        Map<String, String> response = new HashMap<>();
                        response.put("token", token);
                        response.put("role", user.getRole());
                        return ResponseEntity.ok(response);
                    }
                    return ResponseEntity.badRequest().body("Mot de passe incorrect !");
                })
                .orElse(ResponseEntity.badRequest().body("Email introuvable !"));
    }
}