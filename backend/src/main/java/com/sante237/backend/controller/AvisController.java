package com.sante237.backend.controller;
import com.sante237.backend.dto.AvisRequestDTO;
import com.sante237.backend.dto.AvisResponseDTO;
import com.sante237.backend.service.IAvisService;
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
@RequestMapping("/api/avis")
@CrossOrigin(origins = "*")
@Tag(name = "Avis", description = "Gestion complète des avis patients")
public class AvisController {

    @Autowired private IAvisService avisService;

    @GetMapping
    @Operation(summary = "Lister tous les avis")
    public ResponseEntity<List<AvisResponseDTO>> getAllAvis() {
        return ResponseEntity.ok(avisService.getAllAvis());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un avis par ID")
    @ApiResponse(responseCode = "200", description = "Avis trouvé")
    @ApiResponse(responseCode = "404", description = "Avis non trouvé")
    public ResponseEntity<AvisResponseDTO> getAvisById(
        @Parameter(description = "ID de l'avis", example = "1") @PathVariable Long id) {
        return new ResponseEntity<>(avisService.getAvisById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Créer un avis")
    @ApiResponse(responseCode = "201", description = "Avis créé avec succès")
    public ResponseEntity<AvisResponseDTO> createAvis(@Valid @RequestBody AvisRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(avisService.createAvis(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un avis")
    @ApiResponse(responseCode = "200", description = "Avis mis à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Avis non trouvé")
    public ResponseEntity<AvisResponseDTO> updateAvis(
        @Parameter(description = "ID de l'avis", example = "1") @PathVariable Long id,
        @Valid @RequestBody AvisRequestDTO dto) {
        try {
            return ResponseEntity.ok(avisService.updateAvis(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un avis")
    @ApiResponse(responseCode = "204", description = "Avis supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Avis non trouvé")
    public ResponseEntity<Void> deleteAvis(
        @Parameter(description = "ID de l'avis", example = "1") @PathVariable Long id) {
        return avisService.deleteAvis(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}