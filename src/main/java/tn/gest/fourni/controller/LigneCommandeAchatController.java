package tn.gest.fourni.controller;

import tn.gest.fourni.models.LigneCommandeAchat;
import tn.gest.fourni.service.LigneCommandeAchatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lignes-commande")
@CrossOrigin(origins = "http://localhost:4200")
public class LigneCommandeAchatController {
	@Autowired
    private  LigneCommandeAchatService ligneCommandeAchatService;

    
    

    /**
     * Crée une nouvelle ligne de commande pour une commande donnée.
     */
    @PostMapping("/commande/{commandeId}")
    public ResponseEntity<LigneCommandeAchat> createLigne(
            @RequestBody LigneCommandeAchat ligne,
            @PathVariable Long commandeId) {
        LigneCommandeAchat createdLigne = ligneCommandeAchatService.createLigneCommande(ligne, commandeId);
        return new ResponseEntity<>(createdLigne, HttpStatus.CREATED);
    }

    /**
     * Récupère toutes les lignes de commande associées à une commande donnée.
     */
    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<LigneCommandeAchat>> getLignesByCommande(@PathVariable Long commandeId) {
        List<LigneCommandeAchat> lignes = ligneCommandeAchatService.getLignesByCommande(commandeId);
        return ResponseEntity.ok(lignes);
    }

    /**
     * Récupère une ligne de commande par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LigneCommandeAchat> getLigneById(@PathVariable Long id) {
        LigneCommandeAchat ligne = ligneCommandeAchatService.getLigneCommandeById(id);
        return ResponseEntity.ok(ligne);
    }

    /**
     * Récupère les lignes de commande contenant un produit donné (par nom).
     */
    @GetMapping("/produit/{produit}")
    public ResponseEntity<List<LigneCommandeAchat>> getLignesByProduit(@PathVariable String produit) {
        List<LigneCommandeAchat> lignes = ligneCommandeAchatService.getLignesByProduit(produit);
        return ResponseEntity.ok(lignes);
    }

    /**
     * Calcule le montant total d'une commande (somme des lignes).
     */
    @GetMapping("/commande/{commandeId}/total")
    public ResponseEntity<Double> getMontantTotalCommande(@PathVariable Long commandeId) {
        Double total = ligneCommandeAchatService.calculateMontantTotalCommande(commandeId);
        return ResponseEntity.ok(total);
    }
}
