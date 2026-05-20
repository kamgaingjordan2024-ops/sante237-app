package com.sante237.backend.service;

import com.sante237.backend.dto.MedecinRequestDTO;
import com.sante237.backend.dto.MedecinResponseDTO;
import com.sante237.backend.model.Medecin;
import com.sante237.backend.model.Hopital;
import com.sante237.backend.model.Specialite;
import com.sante237.backend.repository.MedecinRepository;
import com.sante237.backend.repository.HopitalRepository;
import com.sante237.backend.repository.SpecialiteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedecinService implements IMedecinService {

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private HopitalRepository hopitalRepository;

    @Autowired
    private SpecialiteRepository specialiteRepository;

    /** Convertit une Entity Medecin en ResponseDTO */
    private MedecinResponseDTO convertToResponseDTO(Medecin medecin) {
        List<String> specialites = medecin.getSpecialites() != null
            ? medecin.getSpecialites().stream().map(Specialite::getNom).collect(Collectors.toList())
            : List.of();

        return new MedecinResponseDTO(
            medecin.getId_utilisateur(),
            medecin.getNom(),
            medecin.getPrenom(),
            medecin.getTelephone(),
            medecin.getEmail(),
            medecin.getMatricule(),
            medecin.getHopital() != null ? medecin.getHopital().getId_hopital() : null,
            medecin.getHopital() != null ? medecin.getHopital().getNom() : null,
            specialites
        );
    }

    /** Convertit un RequestDTO en Entity Medecin */
    private Medecin convertToEntity(MedecinRequestDTO dto) {
        Medecin medecin = new Medecin();
        medecin.setNom(dto.getNom());
        medecin.setPrenom(dto.getPrenom());
        medecin.setTelephone(dto.getTelephone());
        medecin.setEmail(dto.getEmail());
        medecin.setMatricule(dto.getMatricule());
        medecin.setRole("MEDECIN");

        if (dto.getHopitalId() != null) {
            Hopital hopital = hopitalRepository.findById(dto.getHopitalId())
                .orElseThrow(() -> new EntityNotFoundException("Hôpital non trouvé avec l'ID: " + dto.getHopitalId()));
            medecin.setHopital(hopital);
        }

        if (dto.getSpecialiteIds() != null && !dto.getSpecialiteIds().isEmpty()) {
            List<Specialite> specialites = specialiteRepository.findAllById(dto.getSpecialiteIds());
            medecin.setSpecialites(specialites);
        }

        return medecin;
    }

    @Override
    public List<MedecinResponseDTO> getAllMedecins() {
        return medecinRepository.findAll()
            .stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public MedecinResponseDTO getMedecinById(Long id) {
        return medecinRepository.findById(id)
            .map(this::convertToResponseDTO)
            .orElseThrow(() -> new EntityNotFoundException("Médecin non trouvé avec l'ID: " + id));
    }

    @Override
    public MedecinResponseDTO createMedecin(MedecinRequestDTO medecinRequestDTO) {
        Medecin medecin = convertToEntity(medecinRequestDTO);
        Medecin savedMedecin = medecinRepository.save(medecin);
        return convertToResponseDTO(savedMedecin);
    }

    @Override
    public MedecinResponseDTO updateMedecin(Long id, MedecinRequestDTO medecinRequestDTO) {
        Optional<Medecin> medecin = medecinRepository.findById(id);
        if (medecin.isPresent()) {
            Medecin existingMedecin = medecin.get();
            existingMedecin.setNom(medecinRequestDTO.getNom());
            existingMedecin.setPrenom(medecinRequestDTO.getPrenom());
            existingMedecin.setTelephone(medecinRequestDTO.getTelephone());
            existingMedecin.setEmail(medecinRequestDTO.getEmail());
            existingMedecin.
            setMatricule(medecinRequestDTO.getMatricule());

            if (medecinRequestDTO.getHopitalId() != null) {
                Hopital hopital = hopitalRepository.findById(medecinRequestDTO.getHopitalId())
                    .orElseThrow(() -> new EntityNotFoundException("Hôpital non trouvé"));
                existingMedecin.setHopital(hopital);
            }

            if (medecinRequestDTO.getSpecialiteIds() != null) {
                List<Specialite> specialites = specialiteRepository.findAllById(medecinRequestDTO.getSpecialiteIds());
                existingMedecin.setSpecialites(specialites);
            }

            Medecin updatedMedecin = medecinRepository.save(existingMedecin);
            return convertToResponseDTO(updatedMedecin);
        }
        throw new RuntimeException("Médecin non trouvé avec l'ID: " + id);
    }

    @Override
    public boolean deleteMedecin(Long id) {
        if (medecinRepository.existsById(id)) {
            medecinRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean medecinExists(Long id) {
        return medecinRepository.existsById(id);
    }
}