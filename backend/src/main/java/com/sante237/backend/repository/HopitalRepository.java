package com.sante237.backend.repository;

import com.sante237.backend.model.Hopital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HopitalRepository extends JpaRepository<Hopital, Long> {
    Optional<Hopital> findByNom(String nom);
}