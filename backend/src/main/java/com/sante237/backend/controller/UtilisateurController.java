package com.sante237.backend.controller;

import com.sante237.backend.model.Medecin;
import com.sante237.backend.model.Patient;
import com.sante237.backend.model.Utilisateur;
import com.sante237.backend.repository.UtilisateurRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "*")
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping
    @Operation(summary = "Lister tous les utilisateurs")
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        return ResponseEntity.ok(utilisateurRepository.findAll());
    }

    @GetMapping("/patients")
    @Operation(summary = "Lister tous les patients")
    public ResponseEntity<List<Utilisateur>> getAllPatients() {
        return ResponseEntity.ok(utilisateurRepository.findByRole("PATIENT"));
    }

    @GetMapping("/medecins")
    @Operation(summary = "Lister tous les médecins")
    public ResponseEntity<List<Utilisateur>> getAllMedecins() {
        return ResponseEntity.ok(utilisateurRepository.findByRole("MEDECIN"));
    }
    @GetMapping("/{id}")
@Operation(summary = "Obtenir un utilisateur par ID")
@ApiResponse(responseCode = "200", description = "Utilisateur trouvé")
@ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
public ResponseEntity<Utilisateur> getUtilisateurById(
    @Parameter(description = "ID de l'utilisateur", example = "1") @PathVariable Long id) {
    return utilisateurRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
}
@PutMapping("/{id}")
@Operation(summary = "Mettre à jour un utilisateur")
@ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès")
@ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
public ResponseEntity<Utilisateur> updateUtilisateur(
    @Parameter(description = "ID de l'utilisateur", example = "1") @PathVariable Long id,
    @RequestBody Utilisateur updated) {
    return utilisateurRepository.findById(id)
        .map(user -> {
            user.setNom(updated.getNom());
            user.setPrenom(updated.getPrenom());
            user.setTelephone(updated.getTelephone());
            user.setEmail(updated.getEmail());
            return ResponseEntity.ok(utilisateurRepository.save(user));
        })
        .orElse(ResponseEntity.notFound().build());
}

    @PutMapping("/{id}/changer-role")
    @Operation(summary = "Changer le rôle d'un utilisateur", 
               description = "Réservé à l'administrateur - change le rôle PATIENT en MEDECIN")
    @ApiResponse(responseCode = "200", description = "Rôle changé avec succès")
    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    @ApiResponse(responseCode = "400", description = "Changement de rôle invalide")
    public ResponseEntity<?> changerRole(
        @Parameter(description = "ID de l'utilisateur", example = "1")
        @PathVariable Long id,
        @RequestParam String nouveauRole
    ) {
        return utilisateurRepository.findById(id)
            .map(user -> {
                if (!nouveauRole.equalsIgnoreCase("MEDECIN") && 
                    !nouveauRole.equalsIgnoreCase("PATIENT")) {
                    return ResponseEntity.badRequest()
                        .body("Rôle invalide ! Seuls PATIENT et MEDECIN sont autorisés.");
                }
                user.setRole(nouveauRole.toUpperCase());
                utilisateurRepository.save(user);
                return ResponseEntity.ok("Rôle changé en " + nouveauRole.toUpperCase() + " avec succès !");
            })
            .orElse(ResponseEntity.notFound().build());
    }
}