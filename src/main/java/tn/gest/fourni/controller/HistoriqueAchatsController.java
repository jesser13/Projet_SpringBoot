package tn.gest.fourni.controller;

import tn.gest.fourni.models.HistoriqueAchats;
import tn.gest.fourni.service.HistoriqueAchatsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historique")
@CrossOrigin(origins = "http://localhost:4200")
public class HistoriqueAchatsController {
	@Autowired
    private  HistoriqueAchatsService historiqueAchatsService;

    

    @PostMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<HistoriqueAchats> createHistorique(
            @RequestBody HistoriqueAchats historique,
            @PathVariable Long fournisseurId) {
        return new ResponseEntity<>(
                historiqueAchatsService.createHistorique(historique, fournisseurId),
                HttpStatus.CREATED);
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