package com.sante237.backend.repository;

import com.sante237.backend.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    @Query("SELECT r FROM RendezVous r LEFT JOIN FETCH r.patient LEFT JOIN FETCH r.medecin LEFT JOIN FETCH r.creneau WHERE r.medecin.idUtilisateur = :medecinId")
    List<RendezVous> findByMedecin_IdUtilisateur(Long medecinId);

    @Query("SELECT r FROM RendezVous r LEFT JOIN FETCH r.patient LEFT JOIN FETCH r.medecin LEFT JOIN FETCH r.creneau WHERE r.patient.idUtilisateur = :patientId")
    List<RendezVous> findByPatient_IdUtilisateur(Long patientId);
}