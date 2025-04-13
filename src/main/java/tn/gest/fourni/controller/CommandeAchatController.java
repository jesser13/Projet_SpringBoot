package tn.gest.fourni.controller;

import tn.gest.fourni.models.CommandeAchat;
import tn.gest.fourni.service.CommandeAchatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "http://localhost:4200")
public class CommandeAchatController {

    private final CommandeAchatService commandeAchatService;

    public CommandeAchatController(CommandeAchatService commandeAchatService) {
        this.commandeAchatService = commandeAchatService;
    }

    @PostMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<CommandeAchat> createCommande(
            @RequestBody CommandeAchat commande,
            @PathVariable Long fournisseurId) {
        return new ResponseEntity<>(
                commandeAchatService.createCommande(commande, fournisseurId),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommandeAchat>> getAllCommandes() {
        return ResponseEntity.ok(commandeAchatService.getAllCommandes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeAchat> getCommandeById(@PathVariable Long id) {
        return ResponseEntity.ok(commandeAchatService.getCommandeById(id));
    }

    @GetMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<List<CommandeAchat>> getByFournisseur(@PathVariable Long fournisseurId) {
        return ResponseEntity.ok(commandeAchatService.getCommandesByFournisseur(fournisseurId));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<CommandeAchat>> getByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(commandeAchatService.getCommandesByStatut(statut));
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<CommandeAchat> updateStatut(
            @PathVariable Long id,
            @RequestParam String statut) {
        return ResponseEntity.ok(commandeAchatService.changeStatutCommande(id, statut));
    }


    @PostMapping("/{id}/valider")
    public ResponseEntity<CommandeAchat> validerCommande(@PathVariable Long id) {
        return ResponseEntity.ok(commandeAchatService.validerCommande(id));
    }
}