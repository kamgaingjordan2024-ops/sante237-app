package com.sante237.backend.repository;

import com.sante237.backend.model.Creneau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CreneauRepository extends JpaRepository<Creneau, Long> {
    List<Creneau> findByDisponibleTrue();
}