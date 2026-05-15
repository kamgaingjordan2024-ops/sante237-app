package com.sante237.backend.repository;

import com.sante237.backend.model.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByStatut(String statut);
}