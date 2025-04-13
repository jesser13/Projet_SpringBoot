package tn.gest.fourni.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.gest.fourni.models.Fournisseur;
import tn.gest.fourni.service.FournisseurService;

@RestController
@RequestMapping("/api/fournisseurs")
@CrossOrigin(origins = "http://localhost:4200")
public class FournisseurController {

    private final FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    @GetMapping
    public ResponseEntity<List<Fournisseur>> getAllFournisseurs() {
        return ResponseEntity.ok(fournisseurService.getAllFournisseurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable Long id) {
        return ResponseEntity.ok(fournisseurService.getFournisseurById(id));
    }

    @PostMapping
    public ResponseEntity<Fournisseur> createFournisseur(@RequestBody Fournisseur fournisseur) {
        return new ResponseEntity<>(fournisseurService.createFournisseur(fournisseur), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable Long id, @RequestBody Fournisseur fournisseur) {
        return ResponseEntity.ok(fournisseurService.updateFournisseur(id, fournisseur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id) {
        fournisseurService.deleteFournisseur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/qualite/{qualite}")
    public ResponseEntity<List<Fournisseur>> getByQualiteService(@PathVariable String qualite) {
        return ResponseEntity.ok(fournisseurService.findFournisseursByQualiteService(qualite));
    }

    @PostMapping("/{id}/evaluation")
    public ResponseEntity<Fournisseur> evaluerFournisseur(@PathVariable Long id, @RequestParam Double note) {
        return ResponseEntity.ok(fournisseurService.evaluerFournisseur(id, note));
    }

    @GetMapping("/top")
    public ResponseEntity<List<Fournisseur>> getTopFournisseurs() {
        return ResponseEntity.ok(fournisseurService.getFournisseursByNoteDesc());
    }
}