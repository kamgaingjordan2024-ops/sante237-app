package com.sante237.backend.service;
import com.sante237.backend.dto.NotificationRequestDTO;
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
import com.sante237.backend.dto.NotificationRequestDTO;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RendezVousService implements IRendezVousService {

    @Autowired private RendezVousRepository rendezVousRepository;
    @Autowired private PatientRepository patientRepository;
    @Autowired private MedecinRepository medecinRepository;
    @Autowired private CreneauRepository creneauRepository;
    @Autowired private NotificationService notificationService;


    private RendezVousResponseDTO convertToResponseDTO(RendezVous r) {
        return new RendezVousResponseDTO(
            r.getId_rendezvous(),
            r.getDateHeure() != null ? r.getDateHeure().toString() : null,
            r.getDuree(), r.getStatut(),
            r.getPatient() != null ? r.getPatient().getIdUtilisateur() : null,
            r.getPatient() != null ? r.getPatient().getNom() + " " + r.getPatient().getPrenom() : null,
            r.getMedecin() != null ? r.getMedecin().getIdUtilisateur() : null,
            r.getMedecin() != null ? r.getMedecin().getNom() + " " + r.getMedecin().getPrenom() : null,
            r.getCreneau() != null ? r.getCreneau().getIdCreneau() : null
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
                .orElseThrow(() -> new EntityNotFoundException("Créneau non trouvé")));

        RendezVous saved = rendezVousRepository.save(existing);
System.out.println("=== DEBUG NOTIFICATION ===");
System.out.println("Statut: " + dto.getStatut());
System.out.println("Patient: " + saved.getPatient());
System.out.println("PatientId reçu: " + dto.getPatientId());
       // Envoyer notification au patient seulement pour CONFIRME ou REJETE
if (saved.getPatient() != null && 
    (dto.getStatut().equals("CONFIRME") || dto.getStatut().equals("REJETE"))) {
    
    String message = dto.getStatut().equals("CONFIRME")
        ? "✅ Votre rendez-vous du " + saved.getDateHeure().toString() + " a été confirmé par le médecin."
        : "❌ Votre rendez-vous du " + saved.getDateHeure().toString() + " a été annulé par le médecin.";

    NotificationRequestDTO notifDto = new NotificationRequestDTO();
    notifDto.setMessage(message);
    notifDto.setLue(false);
    notifDto.setUtilisateurId(saved.getPatient().getIdUtilisateur());
    notificationService.createNotification(notifDto);
}

        return convertToResponseDTO(saved);
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
@Override
@Transactional
public List<RendezVousResponseDTO> getRendezVousByMedecin(Long medecinId) {
    return rendezVousRepository.findByMedecin_IdUtilisateur(medecinId)
        .stream().map(this::convertToResponseDTO).collect(Collectors.toList());
}
}