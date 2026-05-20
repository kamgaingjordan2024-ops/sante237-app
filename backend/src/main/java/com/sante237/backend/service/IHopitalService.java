package com.sante237.backend.service;

import com.sante237.backend.dto.HopitalRequestDTO;
import com.sante237.backend.dto.HopitalResponseDTO;
import java.util.List;
import java.util.Optional;

public interface IHopitalService {

    /**
     * Récupère la liste de tous les hôpitaux
     */
    List<HopitalResponseDTO> getAllHopitaux();

    /**
     * Récupère un hôpital par son ID
     */
    HopitalResponseDTO getHopitalById(Long id);

    /**
     * Crée un nouvel hôpital
     */
    HopitalResponseDTO createHopital(HopitalRequestDTO hopitalRequestDTO);

    /**
     * Met à jour un hôpital existant
     */
    HopitalResponseDTO updateHopital(Long id, HopitalRequestDTO hopitalRequestDTO);

    /**
     * Supprime un hôpital
     */
    boolean deleteHopital(Long id);

    /**
     * Vérifie si un hôpital existe
     */
    boolean hopitalExists(Long id);
}
