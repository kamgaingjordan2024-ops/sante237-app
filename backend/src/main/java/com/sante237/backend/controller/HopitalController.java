package com.sante237.backend.controller;

import com.sante237.backend.dto.HopitalRequestDTO;
import com.sante237.backend.dto.HopitalResponseDTO;
import com.sante237.backend.service.IHopitalService;
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
@RequestMapping("/api/hopitaux")
@CrossOrigin(origins = "*")
@Tag(name = "Hôpitaux", description = "Gestion complète des hôpitaux")
public class HopitalController {

    @Autowired
    private IHopitalService hopitalService;

    @GetMapping
    @Operation(
        summary = "Lister tous les hôpitaux",
        description = "Récupère la liste complète des hôpitaux enregistrés"
    )
    @ApiResponse(responseCode = "200", description = "Liste des hôpitaux récupérée avec succès")
    public ResponseEntity<List<HopitalResponseDTO>> getAllHopitaux() {
        List<HopitalResponseDTO> hopitaux = hopitalService.getAllHopitaux();
        return ResponseEntity.ok(hopitaux);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtenir un hôpital par ID",
        description = "Récupère les détails d'un hôpital spécifique via son identifiant"
    )
    @ApiResponse(responseCode = "200", description = "Hôpital trouvé")
    @ApiResponse(responseCode = "404", description = "Hôpital non trouvé")
    public ResponseEntity<HopitalResponseDTO> getHopitalById(
        @Parameter(description = "ID de l'hôpital", example = "1")
        @PathVariable Long id
    ) {
        return new ResponseEntity<>(hopitalService.getHopitalById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
        summary = "Créer un nouvel hôpital",
        description = "Ajoute un nouvel hôpital à la base de données"
    )
    @ApiResponse(responseCode = "201", description = "Hôpital créé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<HopitalResponseDTO> createHopital(
        @Valid @RequestBody HopitalRequestDTO hopitalDTO
    ) {
        HopitalResponseDTO createdHopital = hopitalService.createHopital(hopitalDTO);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(createdHopital);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Mettre à jour un hôpital",
        description = "Modifie les informations d'un hôpital existant"
    )
    @ApiResponse(responseCode = "200", description = "Hôpital mis à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Hôpital non trouvé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<HopitalResponseDTO> updateHopital(
        @Parameter(description = "ID de l'hôpital", example = "1")
        @PathVariable Long id,
        @Valid @RequestBody HopitalRequestDTO hopitalRequestDTO
    ) {
        try {
            HopitalResponseDTO updatedHopital = hopitalService.updateHopital(id, hopitalRequestDTO);
            return ResponseEntity.ok(updatedHopital);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Supprimer un hôpital",
        description = "Supprime définitivement un hôpital de la base de données"
    )
    @ApiResponse(responseCode = "204", description = "Hôpital supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Hôpital non trouvé")
    public ResponseEntity<Void> deleteHopital(
        @Parameter(description = "ID de l'hôpital", example = "1")
        @PathVariable Long id
    ) {
        boolean deleted = hopitalService.deleteHopital(id);
        return deleted
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}