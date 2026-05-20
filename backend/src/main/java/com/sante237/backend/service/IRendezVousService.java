package com.sante237.backend.service;
import com.sante237.backend.dto.RendezVousRequestDTO;
import com.sante237.backend.dto.RendezVousResponseDTO;
import java.util.List;

public interface IRendezVousService {
    List<RendezVousResponseDTO> getAllRendezVous();
    RendezVousResponseDTO getRendezVousById(Long id);
    RendezVousResponseDTO createRendezVous(RendezVousRequestDTO dto);
    RendezVousResponseDTO updateRendezVous(Long id, RendezVousRequestDTO dto);
    boolean deleteRendezVous(Long id);
    boolean rendezVousExists(Long id);
}