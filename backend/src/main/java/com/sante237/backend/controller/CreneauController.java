package com.sante237.backend.controller;

import com.sante237.backend.model.Creneau;
import com.sante237.backend.repository.CreneauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/creneaux")
@CrossOrigin(origins = "*")
public class CreneauController {

    @Autowired
    private CreneauRepository creneauRepository;

    @GetMapping
    public List<Creneau> getAll() { return creneauRepository.findAll(); }

    @GetMapping("/disponibles")
    public List<Creneau> getDisponibles() { return creneauRepository.findByDisponibleTrue(); }

    @PostMapping
    public Creneau create(@RequestBody Creneau creneau) {
        return creneauRepository.save(creneau);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        creneauRepository.deleteById(id);
        return ResponseEntity.ok("Supprimé");
    }
}