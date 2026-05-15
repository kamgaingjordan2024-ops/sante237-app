package com.sante237.backend.controller;

import com.sante237.backend.model.Medecin;
import com.sante237.backend.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medecins")
@CrossOrigin(origins = "*")
public class MedecinController {

    @Autowired
    private MedecinRepository medecinRepository;

    @GetMapping
    public List<Medecin> getAll() { return medecinRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Medecin> getById(@PathVariable Long id) {
        return medecinRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Medecin create(@RequestBody Medecin medecin) {
        return medecinRepository.save(medecin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        medecinRepository.deleteById(id);
        return ResponseEntity.ok("Supprimé");
    }
}