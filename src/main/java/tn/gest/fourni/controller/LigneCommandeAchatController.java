package tn.gest.fourni.controller;

import tn.gest.fourni.models.LigneCommandeAchat;
import tn.gest.fourni.service.LigneCommandeAchatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lignes-commande")
@CrossOrigin(origins = "http://localhost:4200")
public class LigneCommandeAchatController {

    private final LigneCommandeAchatService ligneCommandeAchatService;

    public LigneCommandeAchatController(LigneCommandeAchatService ligneCommandeAchatService) {
        this.ligneCommandeAchatService = ligneCommandeAchatService;
    }

    @PostMapping("/commande/{commandeId}")
    public ResponseEntity<LigneCommandeAchat> createLigne(
            @RequestBody LigneCommandeAchat ligne,
            @PathVariable Long commandeId) {
        return new ResponseEntity<>(
                ligneCommandeAchatService.createLigneCommande(ligne, commandeId),
                HttpStatus.CREATED);
    }

    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<LigneCommandeAchat>> getByCommande(@PathVariable Long commandeId) {
        return ResponseEntity.ok(ligneCommandeAchatService.getLignesByCommande(commandeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LigneCommandeAchat> getLigneById(@PathVariable Long id) {
        return ResponseEntity.ok(ligneCommandeAchatService.getLigneCommandeById(id));
    }

    @GetMapping("/produit/{produit}")
    public ResponseEntity<List<LigneCommandeAchat>> getByProduit(@PathVariable String produit) {
        return ResponseEntity.ok(ligneCommandeAchatService.getLignesByProduit(produit));
    }

    @GetMapping("/commande/{commandeId}/total")
    public ResponseEntity<Double> getMontantTotal(@PathVariable Long commandeId) {
        return ResponseEntity.ok(ligneCommandeAchatService.calculateMontantTotalCommande(commandeId));
    }
}