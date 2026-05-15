package com.sante237.backend.controller;

import com.sante237.backend.model.Specialite;
import com.sante237.backend.repository.SpecialiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/specialites")
@CrossOrigin(origins = "*")
public class SpecialiteController {

    @Autowired
    private SpecialiteRepository specialiteRepository;

    @GetMapping
    public List<Specialite> getAll() { return specialiteRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Specialite> getById(@PathVariable Long id) {
        return specialiteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Specialite create(@RequestBody Specialite specialite) {
        return specialiteRepository.save(specialite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        specialiteRepository.deleteById(id);
        return ResponseEntity.ok("Supprimé");
    }
}