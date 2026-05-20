package com.sante237.backend.controller;
import com.sante237.backend.dto.SpecialiteRequestDTO;
import com.sante237.backend.dto.SpecialiteResponseDTO;
import com.sante237.backend.service.ISpecialiteService;
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
@RequestMapping("/api/specialites")
@CrossOrigin(origins = "*")
@Tag(name = "Spécialités", description = "Gestion complète des spécialités médicales")
public class SpecialiteController {

    @Autowired
    private ISpecialiteService specialiteService;

    @GetMapping
    @Operation(summary = "Lister toutes les spécialités")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    public ResponseEntity<List<SpecialiteResponseDTO>> getAllSpecialites() {
        return ResponseEntity.ok(specialiteService.getAllSpecialites());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une spécialité par ID")
    @ApiResponse(responseCode = "200", description = "Spécialité trouvée")
    @ApiResponse(responseCode = "404", description = "Spécialité non trouvée")
    public ResponseEntity<SpecialiteResponseDTO> getSpecialiteById(
        @Parameter(description = "ID de la spécialité", example = "1") @PathVariable Long id) {
        return new ResponseEntity<>(specialiteService.getSpecialiteById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle spécialité")
    @ApiResponse(responseCode = "201", description = "Spécialité créée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<SpecialiteResponseDTO> createSpecialite(@Valid @RequestBody SpecialiteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(specialiteService.createSpecialite(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une spécialité")
    @ApiResponse(responseCode = "200", description = "Spécialité mise à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Spécialité non trouvée")
    public ResponseEntity<SpecialiteResponseDTO> updateSpecialite(
        @Parameter(description = "ID de la spécialité", example = "1") @PathVariable Long id,
        @Valid @RequestBody SpecialiteRequestDTO dto) {
        try {
            return ResponseEntity.ok(specialiteService.updateSpecialite(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une spécialité")
    @ApiResponse(responseCode = "204", description = "Spécialité supprimée avec succès")
    @ApiResponse(responseCode = "404", description = "Spécialité non trouvée")
    public ResponseEntity<Void> deleteSpecialite(
        @Parameter(description = "ID de la spécialité", example = "1") @PathVariable Long id) {
        return specialiteService.deleteSpecialite(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}