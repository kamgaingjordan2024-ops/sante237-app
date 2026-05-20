package com.sante237.backend.service;

import com.sante237.backend.dto.MedecinRequestDTO;
import com.sante237.backend.dto.MedecinResponseDTO;
import java.util.List;

public interface IMedecinService {

    /** Récupère la liste de tous les médecins */
    List<MedecinResponseDTO> getAllMedecins();

    /** Récupère un médecin par son ID */
    MedecinResponseDTO getMedecinById(Long id);

    /** Crée un nouveau médecin */
    MedecinResponseDTO createMedecin(MedecinRequestDTO medecinRequestDTO);

    /** Met à jour un médecin existant */
    MedecinResponseDTO updateMedecin(Long id, MedecinRequestDTO medecinRequestDTO);

    /** Supprime un médecin */
    boolean deleteMedecin(Long id);

    /** Vérifie si un médecin existe */
    boolean medecinExists(Long id);
}