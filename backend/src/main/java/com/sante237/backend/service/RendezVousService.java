package com.sante237.backend.service;
import com.sante237.backend.dto.RendezVousRequestDTO;
import com.sante237.backend.dto.RendezVousResponseDTO;
import com.sante237.backend.model.*;
import com.sante237.backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RendezVousService implements IRendezVousService {

    @Autowired private RendezVousRepository rendezVousRepository;
    @Autowired private PatientRepository patientRepository;
    @Autowired private MedecinRepository medecinRepository;
    @Autowired private CreneauRepository creneauRepository;

    private RendezVousResponseDTO convertToResponseDTO(RendezVous r) {
        return new RendezVousResponseDTO(
            r.getId_rendezvous(),
            r.getDateHeure() != null ? r.getDateHeure().toString() : null,
            r.getDuree(), r.getStatut(),
            r.getPatient() != null ? r.getPatient().getId_utilisateur() : null,
            r.getPatient() != null ? r.getPatient().getNom() + " " + r.getPatient().getPrenom() : null,
            r.getMedecin() != null ? r.getMedecin().getId_utilisateur() : null,
            r.getMedecin() != null ? r.getMedecin().getNom() + " " + r.getMedecin().getPrenom() : null,
            r.getCreneau() != null ? r.getCreneau().getId_creneau() : null
        );
    }

    private RendezVous convertToEntity(RendezVousRequestDTO dto) {
        RendezVous rv = new RendezVous();
        rv.setDateHeure(LocalDateTime.parse(dto.getDateHeure()));
        rv.setDuree(dto.getDuree());
        rv.setStatut(dto.getStatut());
        if (dto.getPatientId() != null)
            rv.setPatient(patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient non trouvé")));
        if (dto.getMedecinId() != null)
            rv.setMedecin(medecinRepository.findById(dto.getMedecinId())
                .orElseThrow(() -> new EntityNotFoundException("Médecin non trouvé")));
        if (dto.getCreneauId() != null)
            rv.setCreneau(creneauRepository.findById(dto.getCreneauId())
                .orElseThrow(() -> new EntityNotFoundException("Créneau non trouvé")));
        return rv;
    }

    @Override
    public List<RendezVousResponseDTO> getAllRendezVous() {
        return rendezVousRepository.findAll().stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public RendezVousResponseDTO getRendezVousById(Long id) {
        return rendezVousRepository.findById(id).map(this::convertToResponseDTO)
            .orElseThrow(() -> new EntityNotFoundException("Rendez-vous non trouvé avec l'ID: " + id));
    }

    @Override
    public RendezVousResponseDTO createRendezVous(RendezVousRequestDTO dto) {
        return convertToResponseDTO(rendezVousRepository.save(convertToEntity(dto)));
    }

    @Override
    public RendezVousResponseDTO updateRendezVous(Long id, RendezVousRequestDTO dto) {
        Optional<RendezVous> rv = rendezVousRepository.findById(id);
        if (rv.isPresent()) {
            RendezVous existing = rv.get();
            existing.setDateHeure(LocalDateTime.parse(dto.getDateHeure()));
            existing.setDuree(dto.getDuree());
            existing.setStatut(dto.getStatut());
            if (dto.getPatientId() != null)
                existing.setPatient(patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new EntityNotFoundException("Patient non trouvé")));
            if (dto.getMedecinId() != null)
                existing.setMedecin(medecinRepository.findById(dto.getMedecinId())
                    .orElseThrow(() -> new EntityNotFoundException("Médecin non trouvé")));
            if (dto.getCreneauId() != null)
                existing.setCreneau(creneauRepository.findById(dto.getCreneauId())
                    .
                    orElseThrow(() -> new EntityNotFoundException("Créneau non trouvé")));
            return convertToResponseDTO(rendezVousRepository.save(existing));
        }
        throw new RuntimeException("Rendez-vous non trouvé avec l'ID: " + id);
    }

    @Override
    public boolean deleteRendezVous(Long id) {
        if (rendezVousRepository.existsById(id)) { rendezVousRepository.deleteById(id); return true; }
        return false;
    }

    @Override
    public boolean rendezVousExists(Long id) { return rendezVousRepository.existsById(id); }
}