package com.sante237.backend.controller;

import com.sante237.backend.model.Hopital;
import com.sante237.backend.repository.HopitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hopitaux")
@CrossOrigin(origins = "*")
public class HopitalController {

    @Autowired
    private HopitalRepository hopitalRepository;

    @GetMapping
    public List<Hopital> getAll() { return hopitalRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Hopital> getById(@PathVariable Long id) {
        return hopitalRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Hopital create(@RequestBody Hopital hopital) {
        return hopitalRepository.save(hopital);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        hopitalRepository.deleteById(id);
        return ResponseEntity.ok("Supprimé");
    }
}