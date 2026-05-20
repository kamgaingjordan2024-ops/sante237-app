package com.sante237.backend.controller;

import com.sante237.backend.dto.PatientRequestDTO;
import com.sante237.backend.dto.PatientResponseDTO;
import com.sante237.backend.service.IPatientService;
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
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
@Tag(name = "Patients", description = "Gestion complète des patients")
public class PatientController {

    @Autowired
    private IPatientService patientService;

    @GetMapping
    @Operation(summary = "Lister tous les patients")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un patient par ID")
    @ApiResponse(responseCode = "200", description = "Patient trouvé")
    @ApiResponse(responseCode = "404", description = "Patient non trouvé")
    public ResponseEntity<PatientResponseDTO> getPatientById(
        @Parameter(description = "ID du patient", example = "1") @PathVariable Long id) {
        return new ResponseEntity<>(patientService.getPatientById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau patient")
    @ApiResponse(responseCode = "201", description = "Patient créé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un patient")
    @ApiResponse(responseCode = "200", description = "Patient mis à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Patient non trouvé")
    public ResponseEntity<PatientResponseDTO> updatePatient(
        @Parameter(description = "ID du patient", example = "1") @PathVariable Long id,
        @Valid @RequestBody PatientRequestDTO dto) {
        try {
            return ResponseEntity.ok(patientService.updatePatient(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un patient")
    @ApiResponse(responseCode = "204", description = "Patient supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Patient non trouvé")
    public ResponseEntity<Void> deletePatient(
        @Parameter(description = "ID du patient", example = "1") @PathVariable Long id) {
        return patientService.deletePatient(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}