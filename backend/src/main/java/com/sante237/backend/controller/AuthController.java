package com.sante237.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import com.sante237.backend.dto.LoginRequest;
import com.sante237.backend.dto.RegisterRequest;
import com.sante237.backend.model.*;
import com.sante237.backend.repository.AdministrateurRepository;
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
    private AdministrateurRepository administrateurRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @Operation(summary = "S'inscrire", description = "Inscription - tout utilisateur devient PATIENT par défaut")
    @ApiResponse(responseCode = "200", description = "Inscription réussie")
    @ApiResponse(responseCode = "400", description = "Email déjà utilisé")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email déjà utilisé !");
        }

        // Tout utilisateur s'inscrit comme PATIENT par défaut
        Patient user = new Patient();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setTelephone(request.getTelephone());
        user.setRole("PATIENT");
        user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));

        utilisateurRepository.save(user);
        return ResponseEntity.ok("Inscription réussie !");
    }

    @PostMapping("/register/admin")
    @Operation(summary = "Créer l'administrateur", description = "Crée le compte administrateur unique du système")
    @ApiResponse(responseCode = "200", description = "Administrateur créé avec succès")
    @ApiResponse(responseCode = "400", description = "Un administrateur existe déjà")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody RegisterRequest request) {
        // Vérifier qu'il n'existe pas déjà un administrateur
        if (administrateurRepository.count() > 0) {
            return ResponseEntity.badRequest().body("Un administrateur existe déjà !");
        }

        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email déjà utilisé !");
        }

        Administrateur admin = new Administrateur();
        admin.setNom(request.getNom());
        admin.setPrenom(request.getPrenom());
        admin.setEmail(request.getEmail());
        admin.setTelephone(request.getTelephone());
        admin.setRole("ADMINISTRATEUR");
        admin.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));

        utilisateurRepository.save(admin);
        return ResponseEntity.ok("Administrateur créé avec succès !");
    }
@PostMapping("/register/medecin")
@Operation(summary = "Créer un médecin", description = "Réservé à l'administrateur - crée un compte médecin")
@ApiResponse(responseCode = "200", description = "Médecin créé avec succès")
@ApiResponse(responseCode = "400", description = "Email déjà utilisé")
public ResponseEntity<?> registerMedecin(@Valid @RequestBody RegisterRequest request) {
    if (utilisateurRepository.existsByEmail(request.getEmail())) {
        return ResponseEntity.badRequest().body("Email déjà utilisé !");
    }

    Medecin medecin = new Medecin();
    medecin.setNom(request.getNom());
    medecin.setPrenom(request.getPrenom());
    medecin.setEmail(request.getEmail());
    medecin.setTelephone(request.getTelephone());
    medecin.setRole("MEDECIN");
    medecin.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));

    utilisateurRepository.save(medecin);
    return ResponseEntity.ok("Médecin créé avec succès !");
}
    @PostMapping("/login")
    @Operation(summary = "Se connecter", description = "Authentification avec email et mot de passe")
    @ApiResponse(responseCode = "200", description = "Connexion réussie, retourne le JWT")
    @ApiResponse(responseCode = "400", description = "Email ou mot de passe incorrect")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return utilisateurRepository.findByEmail(request.getEmail())
                .map(user -> {
                    if (passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())) {
                        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
                        Map<String, String> response = new HashMap<>();
                        response.put("token", token);
                        response.put("role", user.getRole());
                        response.put("id", user.getIdUtilisateur().toString());
                        return ResponseEntity.ok(response);
                    }
                    return ResponseEntity.badRequest().body("Mot de passe incorrect !");
                })
                .orElse(ResponseEntity.badRequest().body("Email introuvable !"));
    }
}