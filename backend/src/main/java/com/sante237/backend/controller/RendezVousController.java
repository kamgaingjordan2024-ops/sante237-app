package com.sante237.backend.controller;

import com.sante237.backend.model.RendezVous;
import com.sante237.backend.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rendezvous")
@CrossOrigin(origins = "*")
public class RendezVousController {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @GetMapping
    public List<RendezVous> getAll() { return rendezVousRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<RendezVous> getById(@PathVariable Long id) {
        return rendezVousRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public RendezVous create(@RequestBody RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }
@PutMapping("/{id}")
public ResponseEntity<?> update(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
    return rendezVousRepository.findById(id).map(existing -> {
        existing.setStatut(body.get("statut"));
        return ResponseEntity.ok(rendezVousRepository.save(existing));
    }).orElse(ResponseEntity.notFound().build());
}
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        rendezVousRepository.deleteById(id);
        return ResponseEntity.ok("Supprimé");
    }
}