package com.sante237.backend.controller;

import com.sante237.backend.dto.MedecinRequestDTO;
import com.sante237.backend.dto.MedecinResponseDTO;
import com.sante237.backend.service.IMedecinService;
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
@RequestMapping("/api/medecins")
@CrossOrigin(origins = "*")
@Tag(name = "Médecins", description = "Gestion complète des médecins")
public class MedecinController {

    @Autowired
    private IMedecinService medecinService;

    @GetMapping
    @Operation(summary = "Lister tous les médecins", description = "Récupère la liste complète des médecins")
    @ApiResponse(responseCode = "200", description = "Liste des médecins récupérée avec succès")
    public ResponseEntity<List<MedecinResponseDTO>> getAllMedecins() {
        return ResponseEntity.ok(medecinService.getAllMedecins());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un médecin par ID", description = "Récupère les détails d'un médecin via son identifiant")
    @ApiResponse(responseCode = "200", description = "Médecin trouvé")
    @ApiResponse(responseCode = "404", description = "Médecin non trouvé")
    public ResponseEntity<MedecinResponseDTO> getMedecinById(
        @Parameter(description = "ID du médecin", example = "1")
        @PathVariable Long id
    ) {
        return new ResponseEntity<>(medecinService.getMedecinById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau médecin", description = "Ajoute un nouveau médecin à la base de données")
    @ApiResponse(responseCode = "201", description = "Médecin créé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<MedecinResponseDTO> createMedecin(
        @Valid @RequestBody MedecinRequestDTO medecinDTO
    ) {
        MedecinResponseDTO created = medecinService.createMedecin(medecinDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un médecin", description = "Modifie les informations d'un médecin existant")
    @ApiResponse(responseCode = "200", description = "Médecin mis à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Médecin non trouvé")
    public ResponseEntity<MedecinResponseDTO> updateMedecin(
        @Parameter(description = "ID du médecin", example = "1")
        @PathVariable Long id,
        @Valid @RequestBody MedecinRequestDTO medecinRequestDTO
    ) {
        try {
            return ResponseEntity.ok(medecinService.updateMedecin(id, medecinRequestDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un médecin", description = "Supprime définitivement un médecin de la base de données")
    @ApiResponse(responseCode = "204", description = "Médecin supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Médecin non trouvé")
    public ResponseEntity<Void> deleteMedecin(
        @Parameter(description = "ID du médecin", example = "1")
        @PathVariable Long id
    ) {
        boolean deleted = medecinService.deleteMedecin(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}