package com.sante237.backend.controller;
import com.sante237.backend.dto.CreneauRequestDTO;
import com.sante237.backend.dto.CreneauResponseDTO;
import com.sante237.backend.service.ICreneauService;
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
@RequestMapping("/api/creneaux")
@CrossOrigin(origins = "*")
@Tag(name = "Créneaux", description = "Gestion complète des créneaux médicaux")
public class CreneauController {

    @Autowired private ICreneauService creneauService;

    @GetMapping
    @Operation(summary = "Lister tous les créneaux")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    public ResponseEntity<List<CreneauResponseDTO>> getAllCreneaux() {
        return ResponseEntity.ok(creneauService.getAllCreneaux());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un créneau par ID")
    @ApiResponse(responseCode = "200", description = "Créneau trouvé")
    @ApiResponse(responseCode = "404", description = "Créneau non trouvé")
    public ResponseEntity<CreneauResponseDTO> getCreneauById(
        @Parameter(description = "ID du créneau", example = "1") @PathVariable Long id) {
        return new ResponseEntity<>(creneauService.getCreneauById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau créneau")
    @ApiResponse(responseCode = "201", description = "Créneau créé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<CreneauResponseDTO> createCreneau(@Valid @RequestBody CreneauRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(creneauService.createCreneau(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un créneau")
    @ApiResponse(responseCode = "200", description = "Créneau mis à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Créneau non trouvé")
    public ResponseEntity<CreneauResponseDTO> updateCreneau(
        @Parameter(description = "ID du créneau", example = "1") @PathVariable Long id,
        @Valid @RequestBody CreneauRequestDTO dto) {
        try {
            return ResponseEntity.ok(creneauService.updateCreneau(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un créneau")
    @ApiResponse(responseCode = "204", description = "Créneau supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Créneau non trouvé")
    public ResponseEntity<Void> deleteCreneau(
        @Parameter(description = "ID du créneau", example = "1") @PathVariable Long id) {
        return creneauService.deleteCreneau(id)
        
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}