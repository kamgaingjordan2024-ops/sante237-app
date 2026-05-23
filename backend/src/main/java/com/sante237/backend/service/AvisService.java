package com.sante237.backend.service;

import com.sante237.backend.dto.AvisRequestDTO;
import com.sante237.backend.dto.AvisResponseDTO;
import com.sante237.backend.model.Avis;
import com.sante237.backend.model.Patient;
import com.sante237.backend.model.RendezVous;
import com.sante237.backend.repository.AvisRepository;
import com.sante237.backend.repository.PatientRepository;
import com.sante237.backend.repository.RendezVousRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvisService implements IAvisService {

    @Autowired
    private AvisRepository avisRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RendezVousRepository rendezVousRepository;

    private AvisResponseDTO convertToResponseDTO(Avis a) {
        return new AvisResponseDTO(
            a.getId_avis(),
            a.getCommentaire(),
            a.getNote(),
            a.getPatient() != null ? a.getPatient().getIdUtilisateur() : null,
            a.getPatient() != null ? a.getPatient().getNom() + " " + a.getPatient().getPrenom() : null,
            a.getRendezVous() != null ? a.getRendezVous().getId_rendezvous() : null
        );
    }

    @Override
    public List<AvisResponseDTO> getAllAvis() {
        return avisRepository.findAll()
            .stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public AvisResponseDTO getAvisById(Long id) {
        return avisRepository.findById(id)
            .map(this::convertToResponseDTO)
            .orElseThrow(() -> new EntityNotFoundException("Avis non trouvé avec l'ID: " + id));
    }

    @Override
    public AvisResponseDTO createAvis(AvisRequestDTO dto) {
        Avis a = new Avis();
        a.setCommentaire(dto.getCommentaire());
        a.setNote(dto.getNote());
        if (dto.getPatientId() != null)
            a.setPatient(patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient non trouvé")));
        if (dto.getRendezVousId() != null)
            a.setRendezVous(rendezVousRepository.findById(dto.getRendezVousId())
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous non trouvé")));
        return convertToResponseDTO(avisRepository.save(a));
    }

    @Override
    public AvisResponseDTO updateAvis(Long id, AvisRequestDTO dto) {
        Optional<Avis> avis = avisRepository.findById(id);
        if (avis.isPresent()) {
            Avis existing = avis.get();
            existing.setCommentaire(dto.getCommentaire());
            existing.setNote(dto.getNote());
            if (dto.getPatientId() != null)
                existing.setPatient(patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new EntityNotFoundException("Patient non trouvé")));
            if (dto.getRendezVousId() != null)
                existing.setRendezVous(rendezVousRepository.findById(dto.getRendezVousId())
                    .orElseThrow(() -> new EntityNotFoundException("Rendez-vous non trouvé")));
            return convertToResponseDTO(avisRepository.save(existing));
        }
        throw new RuntimeException("Avis non trouvé avec l'ID: " + id);
    }

    @Override
    public boolean deleteAvis(Long id) {
        if (avisRepository.existsById(id)) {
            avisRepository.deleteById(id);
            return true;
        }
        return false;
    }
}