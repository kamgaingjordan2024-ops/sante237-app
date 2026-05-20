package com.sante237.backend.service;

import com.sante237.backend.dto.AvisRequestDTO;
import com.sante237.backend.dto.AvisResponseDTO;
import java.util.List;

public interface IAvisService {

    /** Récupère la liste de tous les avis */
    List<AvisResponseDTO> getAllAvis();

    /** Récupère un avis par son ID */
    AvisResponseDTO getAvisById(Long id);

    /** Crée un nouvel avis */
    AvisResponseDTO createAvis(AvisRequestDTO dto);

    /** Met à jour un avis existant */
    AvisResponseDTO updateAvis(Long id, AvisRequestDTO dto);

    /** Supprime un avis */
    boolean deleteAvis(Long id);
}