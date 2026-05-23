package com.sante237.backend.service;
import com.sante237.backend.dto.SpecialiteRequestDTO;
import com.sante237.backend.dto.SpecialiteResponseDTO;
import com.sante237.backend.model.Specialite;
import com.sante237.backend.repository.SpecialiteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpecialiteService implements ISpecialiteService {

    @Autowired
    private SpecialiteRepository specialiteRepository;

    private SpecialiteResponseDTO convertToResponseDTO(Specialite s) {
        return new SpecialiteResponseDTO(s.getIdSpecialite(), s.getNom());
    }

    @Override
    public List<SpecialiteResponseDTO> getAllSpecialites() {
        return specialiteRepository.findAll().stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public SpecialiteResponseDTO getSpecialiteById(Long id) {
        return specialiteRepository.findById(id).map(this::convertToResponseDTO)
            .orElseThrow(() -> new EntityNotFoundException("Spécialité non trouvée avec l'ID: " + id));
    }

    @Override
    public SpecialiteResponseDTO createSpecialite(SpecialiteRequestDTO dto) {
        Specialite s = new Specialite();
        s.setNom(dto.getNom());
        return convertToResponseDTO(specialiteRepository.save(s));
    }

    @Override
    public SpecialiteResponseDTO updateSpecialite(Long id, SpecialiteRequestDTO dto) {
        Optional<Specialite> specialite = specialiteRepository.findById(id);
        if (specialite.isPresent()) {
            Specialite existing = specialite.get();
            existing.setNom(dto.getNom());
            return convertToResponseDTO(specialiteRepository.save(existing));
        }
        throw new RuntimeException("Spécialité non trouvée avec l'ID: " + id);
    }

    @Override
    public boolean deleteSpecialite(Long id) {
        if (specialiteRepository.existsById(id)) {
            specialiteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean specialiteExists(Long id) {
        return specialiteRepository.existsById(id);
    }
}