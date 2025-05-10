package tn.gest.fourni.controller;

import tn.gest.fourni.models.CommandeAchat;
import tn.gest.fourni.service.CommandeAchatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "http://localhost:4200")
public class CommandeAchatController {

    @Autowired
    private CommandeAchatService commandeAchatService;

    @PostMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<CommandeAchat> createCommande(
            @RequestBody CommandeAchat commande,
            @PathVariable Long fournisseurId) {
        CommandeAchat createdCommande = commandeAchatService.createCommande(commande, fournisseurId);
        return new ResponseEntity<>(createdCommande, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommandeAchat> updateCommande(
            @PathVariable Long id,
            @RequestBody CommandeAchat commandeDetails) {
        CommandeAchat updatedCommande = commandeAchatService.updateCommande(id, commandeDetails);
        return ResponseEntity.ok(updatedCommande);
    }

    @GetMapping
    public ResponseEntity<List<CommandeAchat>> getAllCommandes() {
        List<CommandeAchat> commandes = commandeAchatService.getAllCommandes();
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeAchat> getCommandeById(@PathVariable Long id) {
        CommandeAchat commande = commandeAchatService.getCommandeById(id);
        return ResponseEntity.ok(commande);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        commandeAchatService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<List<CommandeAchat>> getCommandesByFournisseur(@PathVariable Long fournisseurId) {
        List<CommandeAchat> commandes = commandeAchatService.getCommandesByFournisseur(fournisseurId);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<CommandeAchat>> getCommandesByStatut(@PathVariable String statut) {
        List<CommandeAchat> commandes = commandeAchatService.getCommandesByStatut(statut);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/dates")
    public ResponseEntity<List<CommandeAchat>> getCommandesBetweenDates(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<CommandeAchat> commandes = commandeAchatService.getCommandesBetweenDates(startDate, endDate);
        return ResponseEntity.ok(commandes);
    }

    @PostMapping("/{id}/valider")
    public ResponseEntity<CommandeAchat> validerCommande(@PathVariable Long id) {
        CommandeAchat commandeValidee = commandeAchatService.validerCommande(id);
        return ResponseEntity.ok(commandeValidee);
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<CommandeAchat> changeStatutCommande(
            @PathVariable Long id,
            @RequestParam String statut) {
        CommandeAchat updatedCommande = commandeAchatService.changeStatutCommande(id, statut);
        return ResponseEntity.ok(updatedCommande);
    }
}