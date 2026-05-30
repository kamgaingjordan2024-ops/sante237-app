package com.sante237.backend.service;
import com.sante237.backend.dto.CreneauRequestDTO;
import com.sante237.backend.dto.CreneauResponseDTO;
import java.util.List;

public interface ICreneauService {
    List<CreneauResponseDTO> getAllCreneaux();
    CreneauResponseDTO getCreneauById(Long id);
    CreneauResponseDTO createCreneau(CreneauRequestDTO dto);
    CreneauResponseDTO updateCreneau(Long id, CreneauRequestDTO dto);
    boolean deleteCreneau(Long id);
    boolean creneauExists(Long id);
      List<CreneauResponseDTO> getCreneauxByMedecin(Long medecinId);
}
