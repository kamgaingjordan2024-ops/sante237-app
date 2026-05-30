package com.sante237.backend.service;
import com.sante237.backend.dto.CreneauRequestDTO;
import com.sante237.backend.dto.CreneauResponseDTO;
import com.sante237.backend.model.Creneau;
import com.sante237.backend.model.Medecin;
import com.sante237.backend.model.Hopital;
import com.sante237.backend.repository.CreneauRepository;
import com.sante237.backend.repository.MedecinRepository;
import com.sante237.backend.repository.HopitalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreneauService implements ICreneauService {

    @Autowired private CreneauRepository creneauRepository;
    @Autowired private MedecinRepository medecinRepository;
    @Autowired private HopitalRepository hopitalRepository;

    private CreneauResponseDTO convertToResponseDTO(Creneau c) {
        return new CreneauResponseDTO(
            c.getIdCreneau(),
            c.getDateHeure() != null ? c.getDateHeure().toString() : null,
            c.getDuree(), c.isDisponible(),
            c.getMedecin() != null ? c.getMedecin().getIdUtilisateur() : null,
            null,
            c.getHopital() != null ? c.getHopital().getIdHopital() : null,
            c.getHopital() != null ? c.getHopital().getNom() : null
        );
    }

    private Creneau convertToEntity(CreneauRequestDTO dto) {
        Creneau creneau = new Creneau();
        creneau.setDateHeure(LocalDateTime.parse(dto.getDateHeure()));
        creneau.setDuree(dto.getDuree());
        creneau.setDisponible(dto.isDisponible());
        if (dto.getMedecinId() != null) {
            Medecin medecin = medecinRepository.findById(dto.getMedecinId())
                .orElseThrow(() -> new EntityNotFoundException("Médecin non trouvé"));
            creneau.setMedecin(medecin);
        }
        if (dto.getHopitalId() != null) {
            Hopital hopital = hopitalRepository.findById(dto.getHopitalId())
                .orElseThrow(() -> new EntityNotFoundException("Hôpital non trouvé"));
            creneau.setHopital(hopital);
        }
        return creneau;
    }

    @Override
    public List<CreneauResponseDTO> getAllCreneaux() {
        return creneauRepository.findAll().stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public CreneauResponseDTO getCreneauById(Long id) {
        return creneauRepository.findById(id).map(this::convertToResponseDTO)
            .orElseThrow(() -> new EntityNotFoundException("Créneau non trouvé avec l'ID: " + id));
    }

    @Override
    public CreneauResponseDTO createCreneau(CreneauRequestDTO dto) {
        return convertToResponseDTO(creneauRepository.save(convertToEntity(dto)));
    }

    @Override
    public CreneauResponseDTO updateCreneau(Long id, CreneauRequestDTO dto) {
        Optional<Creneau> creneau = creneauRepository.findById(id);
        if (creneau.isPresent()) {
            Creneau existing = creneau.get();
            existing.setDateHeure(LocalDateTime.parse(dto.getDateHeure()));
            existing.setDuree(dto.getDuree());
            existing.setDisponible(dto.isDisponible());
            if (dto.getMedecinId() != null) {
                Medecin medecin = medecinRepository.findById(dto.getMedecinId())
                    .orElseThrow(() -> new EntityNotFoundException("Médecin non trouvé"));
                existing.setMedecin(medecin);
            }
            if (dto.getHopitalId() != null) {
                Hopital hopital = hopitalRepository.findById(dto.getHopitalId())
                    .orElseThrow(() -> new EntityNotFoundException("Hôpital non trouvé"));
                existing.setHopital(hopital);
            }
            return convertToResponseDTO(creneauRepository.save(existing));
        }throw new RuntimeException("Créneau non trouvé avec l'ID: " + id);
    }

    @Override
    public boolean deleteCreneau(Long id) {
        if (creneauRepository.existsById(id)) { creneauRepository.deleteById(id); return true; }
        return false;
    }

    @Override
    public boolean creneauExists(Long id) { return creneauRepository.existsById(id); }
@Override
public List<CreneauResponseDTO> getCreneauxByMedecin(Long medecinId) {
    return creneauRepository.findByMedecin_IdUtilisateur(medecinId)
        .stream().map(this::convertToResponseDTO).collect(Collectors.toList());
}
}