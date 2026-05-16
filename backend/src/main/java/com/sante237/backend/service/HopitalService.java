package com.sante237.backend.service;

import com.sante237.backend.dto.HopitalRequestDTO;
import com.sante237.backend.dto.HopitalResponseDTO;
import com.sante237.backend.model.Hopital;
import com.sante237.backend.repository.HopitalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HopitalService implements IHopitalService {

    @Autowired
    private HopitalRepository hopitalRepository;

    /**
     * Convertit une Entity Hopital en ResponseDTO
     */
    private HopitalResponseDTO convertToResponseDTO(Hopital hopital) {
        return new HopitalResponseDTO(
            hopital.getId_hopital(),
            hopital.getNom(),
            hopital.getAdresse(),
            hopital.getTelephone()
        );
    }

    /**
     * Convertit un RequestDTO en Entity Hopital
     */
    private Hopital convertToEntity(HopitalRequestDTO hopitalRequestDTO) {
        Hopital hopital = new Hopital();
        hopital.setNom(hopitalRequestDTO.getNom());
        hopital.setAdresse(hopitalRequestDTO.getAdresse());
        hopital.setTelephone(hopitalRequestDTO.getTelephone());
        return hopital;
    }

    @Override
    public List<HopitalResponseDTO> getAllHopitaux() {
        return hopitalRepository.findAll()
            .stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public HopitalResponseDTO getHopitalById(Long id) {
        return hopitalRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Hôpital non trouvé avec l'ID: " + id));
    }


    @Override
    public HopitalResponseDTO createHopital(HopitalRequestDTO hopitalRequestDTO) {
        Hopital hopital = convertToEntity(hopitalRequestDTO);
        Hopital savedHopital = hopitalRepository.save(hopital);
        return convertToResponseDTO(savedHopital);
    }

    @Override
    public HopitalResponseDTO updateHopital(Long id, HopitalRequestDTO hopitalRequestDTO) {
        Optional<Hopital> hopital = hopitalRepository.findById(id);
        if (hopital.isPresent()) {
            Hopital existingHopital = hopital.get();
            existingHopital.setNom(hopitalRequestDTO.getNom());
            existingHopital.setAdresse(hopitalRequestDTO.getAdresse());
            existingHopital.setTelephone(hopitalRequestDTO.getTelephone());
            Hopital updatedHopital = hopitalRepository.save(existingHopital);
            return convertToResponseDTO(updatedHopital);
        }
        throw new RuntimeException("Hôpital non trouvé avec l'ID: " + id);
    }

    @Override
    public boolean deleteHopital(Long id) {
        if (hopitalRepository.existsById(id)) {
            hopitalRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean hopitalExists(Long id) {
        return hopitalRepository.existsById(id);
    }
}

