package tn.gest.fourni.controller;

import tn.gest.fourni.models.CommandeAchat;
import tn.gest.fourni.service.CommandeAchatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "http://localhost:4200")
public class CommandeAchatController {
	@Autowired
    private  CommandeAchatService commandeAchatService;

    
  

    /**
     * Crée une commande pour un fournisseur donné
     */
    @PostMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<CommandeAchat> createCommande(
            @RequestBody CommandeAchat commande,
            @PathVariable Long fournisseurId) {
        CommandeAchat createdCommande = commandeAchatService.createCommande(commande, fournisseurId);
        return new ResponseEntity<>(createdCommande, HttpStatus.CREATED);
    }

    /**
     * Récupère toutes les commandes
     */
    @GetMapping
    public ResponseEntity<List<CommandeAchat>> getAllCommandes() {
        List<CommandeAchat> commandes = commandeAchatService.getAllCommandes();
        return ResponseEntity.ok(commandes);
    }

    /**
     * Récupère une commande par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommandeAchat> getCommandeById(@PathVariable Long id) {
        CommandeAchat commande = commandeAchatService.getCommandeById(id);
        return ResponseEntity.ok(commande);
    }

    /**
     * Récupère toutes les commandes d'un fournisseur spécifique
     */
    @GetMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<List<CommandeAchat>> getCommandesByFournisseur(@PathVariable Long fournisseurId) {
        List<CommandeAchat> commandes = commandeAchatService.getCommandesByFournisseur(fournisseurId);
        return ResponseEntity.ok(commandes);
    }

    /**
     * Récupère toutes les commandes ayant un statut donné
     */
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<CommandeAchat>> getCommandesByStatut(@PathVariable String statut) {
        List<CommandeAchat> commandes = commandeAchatService.getCommandesByStatut(statut);
        return ResponseEntity.ok(commandes);
    }

    /**
     * Met à jour le statut d'une commande
     */
    @PutMapping("/{id}/statut")
    public ResponseEntity<CommandeAchat> updateStatut(
            @PathVariable Long id,
            @RequestParam String statut) {
        CommandeAchat updatedCommande = commandeAchatService.changeStatutCommande(id, statut);
        return ResponseEntity.ok(updatedCommande);
    }

    /**
     * Valide une commande (change son statut à "Validée" ou équivalent)
     */
    @PostMapping("/{id}/valider")
    public ResponseEntity<CommandeAchat> validerCommande(@PathVariable Long id) {
        CommandeAchat commandeValidee = commandeAchatService.validerCommande(id);
        return ResponseEntity.ok(commandeValidee);
    }
}
