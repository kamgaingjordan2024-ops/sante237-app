package com.sante237.backend.service;

import com.sante237.backend.dto.PatientRequestDTO;
import com.sante237.backend.dto.PatientResponseDTO;
import java.util.List;

public interface IPatientService {

    /** Récupère la liste de tous les patients */
    List<PatientResponseDTO> getAllPatients();

    /** Récupère un patient par son ID */
    PatientResponseDTO getPatientById(Long id);

    /** Crée un nouveau patient */
    PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);

    /** Met à jour un patient existant */
    PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO);

    /** Supprime un patient */
    boolean deletePatient(Long id);

    /** Vérifie si un patient existe */
    boolean patientExists(Long id);
}