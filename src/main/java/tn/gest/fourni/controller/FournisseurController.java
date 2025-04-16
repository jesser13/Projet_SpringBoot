package tn.gest.fourni.controller;

import tn.gest.fourni.DTO.FournisseurDTO;
import tn.gest.fourni.service.FournisseurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
@CrossOrigin(origins = "http://localhost:4200")
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    @GetMapping
    public ResponseEntity<List<FournisseurDTO>> getAllFournisseurs() {
        return ResponseEntity.ok(fournisseurService.getAllFournisseurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FournisseurDTO> getFournisseurById(@PathVariable Long id) {
        return ResponseEntity.ok(fournisseurService.getFournisseurById(id));
    }

    @PostMapping
    public ResponseEntity<FournisseurDTO> createFournisseur(@RequestBody FournisseurDTO fournisseurDTO) {
        FournisseurDTO created = fournisseurService.createFournisseur(fournisseurDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FournisseurDTO> updateFournisseur(@PathVariable Long id, @RequestBody FournisseurDTO fournisseurDTO) {
        FournisseurDTO updated = fournisseurService.updateFournisseur(id, fournisseurDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id) {
        fournisseurService.deleteFournisseur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/qualite/{qualite}")
    public ResponseEntity<List<FournisseurDTO>> getByQualiteService(@PathVariable String qualite) {
        return ResponseEntity.ok(fournisseurService.findFournisseursByQualiteService(qualite));
    }

    @PostMapping("/{id}/evaluation")
    public ResponseEntity<FournisseurDTO> evaluerFournisseur(@PathVariable Long id, @RequestParam Double note) {
        FournisseurDTO evaluated = fournisseurService.evaluerFournisseur(id, note);
        return ResponseEntity.ok(evaluated);
    }

    @GetMapping("/top")
    public ResponseEntity<List<FournisseurDTO>> getTopFournisseurs() {
        return ResponseEntity.ok(fournisseurService.getFournisseursByNoteDesc());
    }
}
