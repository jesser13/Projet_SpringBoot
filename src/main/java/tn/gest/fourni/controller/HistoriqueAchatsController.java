package tn.gest.fourni.controller;

import tn.gest.fourni.models.HistoriqueAchats;
import tn.gest.fourni.service.HistoriqueAchatsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historique")
@CrossOrigin(origins = "http://localhost:4200")
public class HistoriqueAchatsController {

    @Autowired
    private HistoriqueAchatsService historiqueAchatsService;

    // ➡️ Récupérer tous les historiques
    @GetMapping
    public ResponseEntity<List<HistoriqueAchats>> getAllHistoriques() {
        return ResponseEntity.ok(historiqueAchatsService.getAllHistoriques());
    }

    // ➡️ Récupérer un historique par son ID
    @GetMapping("/{id}")
    public ResponseEntity<HistoriqueAchats> getHistoriqueById(@PathVariable Long id) {
        return ResponseEntity.ok(historiqueAchatsService.getHistoriqueById(id));
    }


    // ➡️ Supprimer un historique
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorique(@PathVariable Long id) {
        historiqueAchatsService.deleteHistorique(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<List<HistoriqueAchats>> getByFournisseur(@PathVariable Long fournisseurId) {
        return ResponseEntity.ok(historiqueAchatsService.getHistoriqueByFournisseur(fournisseurId));
    }

    @GetMapping("/produit/{produit}")
    public ResponseEntity<List<HistoriqueAchats>> getByProduit(@PathVariable String produit) {
        return ResponseEntity.ok(historiqueAchatsService.getHistoriqueByProduit(produit));
    }

    @GetMapping("/fournisseur/{fournisseurId}/delai-moyen")
    public ResponseEntity<Double> getDelaiMoyen(@PathVariable Long fournisseurId) {
        return ResponseEntity.ok(historiqueAchatsService.getMoyenneDelaiLivraison(fournisseurId));
    }

    @GetMapping("/fournisseur/{fournisseurId}/performance")
    public ResponseEntity<Double> getPerformance(@PathVariable Long fournisseurId) {
        return ResponseEntity.ok(historiqueAchatsService.calculerPerformanceFournisseur(fournisseurId));
    }

    @GetMapping("/top-produits")
    public ResponseEntity<List<Object[]>> getTopProduits() {
        return ResponseEntity.ok(historiqueAchatsService.getProduitsPlusCommandes());
    }
}
