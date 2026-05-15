package com.sante237.backend.repository;

import com.sante237.backend.model.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {
}