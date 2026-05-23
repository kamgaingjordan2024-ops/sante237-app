package com.sante237.backend.service;

import com.sante237.backend.dto.PatientRequestDTO;
import com.sante237.backend.dto.PatientResponseDTO;
import com.sante237.backend.model.Patient;
import com.sante237.backend.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    private PatientResponseDTO convertToResponseDTO(Patient patient) {
        return new PatientResponseDTO(
            patient.getIdUtilisateur(),
            patient.getNom(),
            patient.getPrenom(),
            patient.getTelephone(),
            patient.getEmail(),
            patient.getAdresse(),
            patient.getDateNaissance()
        );
    }

    private Patient convertToEntity(PatientRequestDTO dto) {
        Patient patient = new Patient();
        patient.setNom(dto.getNom());
        patient.setPrenom(dto.getPrenom());
        patient.setTelephone(dto.getTelephone());
        patient.setEmail(dto.getEmail());
        patient.setAdresse(dto.getAdresse());
        patient.setDateNaissance(dto.getDateNaissance());
        patient.setRole("PATIENT");
        return patient;
    }

    @Override
    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll()
            .stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    public PatientResponseDTO getPatientById(Long id) {
        return patientRepository.findById(id)
            .map(this::convertToResponseDTO)
            .orElseThrow(() -> new EntityNotFoundException("Patient non trouvé avec l'ID: " + id));
    }

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        Patient patient = convertToEntity(patientRequestDTO);
        Patient saved = patientRepository.save(patient);
        return convertToResponseDTO(saved);
    }

    @Override
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            Patient existing = patient.get();
            existing.setNom(patientRequestDTO.getNom());
            existing.setPrenom(patientRequestDTO.getPrenom());
            existing.setTelephone(patientRequestDTO.getTelephone());
            existing.setEmail(patientRequestDTO.getEmail());
            existing.setAdresse(patientRequestDTO.getAdresse());
            existing.setDateNaissance(patientRequestDTO.getDateNaissance());
            return convertToResponseDTO(patientRepository.save(existing));
        }
        throw new RuntimeException("Patient non trouvé avec l'ID: " + id);
    }

    @Override
    public boolean deletePatient(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean patientExists(Long id) {
        return patientRepository.existsById(id);
    }
}