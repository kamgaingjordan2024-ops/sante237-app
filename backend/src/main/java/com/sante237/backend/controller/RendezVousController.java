package com.sante237.backend.controller;
import com.sante237.backend.dto.RendezVousRequestDTO;
import com.sante237.backend.dto.RendezVousResponseDTO;
import com.sante237.backend.service.IRendezVousService;
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
@RequestMapping("/api/rendezvous")
@CrossOrigin(origins = "*")
@Tag(name = "Rendez-vous", description = "Gestion complète des rendez-vous")
public class RendezVousController {

    @Autowired private IRendezVousService rendezVousService;

    @GetMapping
    @Operation(summary = "Lister tous les rendez-vous")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    public ResponseEntity<List<RendezVousResponseDTO>> getAllRendezVous() {
        return ResponseEntity.ok(rendezVousService.getAllRendezVous());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un rendez-vous par ID")
    @ApiResponse(responseCode = "200", description = "Rendez-vous trouvé")
    @ApiResponse(responseCode = "404", description = "Rendez-vous non trouvé")
    public ResponseEntity<RendezVousResponseDTO> getRendezVousById(
        @Parameter(description = "ID du rendez-vous", example = "1") @PathVariable Long id) {
        return new ResponseEntity<>(rendezVousService.getRendezVousById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau rendez-vous")
    @ApiResponse(responseCode = "201", description = "Rendez-vous créé avec succès")
    public ResponseEntity<RendezVousResponseDTO> createRendezVous(@Valid @RequestBody RendezVousRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rendezVousService.createRendezVous(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un rendez-vous")
    @ApiResponse(responseCode = "200", description = "Rendez-vous mis à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Rendez-vous non trouvé")
    public ResponseEntity<RendezVousResponseDTO> updateRendezVous(
        @Parameter(description = "ID du rendez-vous", example = "1") @PathVariable Long id,
        @Valid @RequestBody RendezVousRequestDTO dto) {
        try {
            return ResponseEntity.ok(rendezVousService.updateRendezVous(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un rendez-vous")
    @ApiResponse(responseCode = "204", description = "Rendez-vous supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Rendez-vous non trouvé")
    public ResponseEntity<Void> deleteRendezVous(
        @Parameter(description = "ID du rendez-vous", example = "1") @PathVariable Long id) {
        return rendezVousService.deleteRendezVous(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}