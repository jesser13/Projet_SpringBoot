package tn.gest.fourni.service;

import tn.gest.fourni.models.CommandeAchat;
import tn.gest.fourni.models.Fournisseur;
import tn.gest.fourni.models.LigneCommandeAchat;
import tn.gest.fourni.repository.CommandeAchatRepository;
import tn.gest.fourni.repository.FournisseurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CommandeAchatService {

    private final CommandeAchatRepository commandeAchatRepository;
    private final FournisseurRepository fournisseurRepository;

    public CommandeAchatService(CommandeAchatRepository commandeAchatRepository,
                              FournisseurRepository fournisseurRepository) {
        this.commandeAchatRepository = commandeAchatRepository;
        this.fournisseurRepository = fournisseurRepository;
    }

    // CRUD Operations

    /**
     * Crée une nouvelle commande
     */
    public CommandeAchat createCommande(CommandeAchat commande, Long fournisseurId) {
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID : " + fournisseurId));

        commande.setFournisseur(fournisseur);
        commande.setDate(LocalDate.now());
        commande.setStatut("EN_COURS");
        commande.setMontant(calculerMontantTotal(commande.getLignes()));

        return commandeAchatRepository.save(commande);
    }

    /**
     * Récupère toutes les commandes
     */
    public List<CommandeAchat> getAllCommandes() {
        return commandeAchatRepository.findAll();
    }

    /**
     * Récupère une commande par son ID
     */
    public CommandeAchat getCommandeById(Long id) {
        return commandeAchatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID : " + id));
    }

    /**
     * Met à jour une commande existante
     */
    public CommandeAchat updateCommande(Long id, CommandeAchat commandeDetails) {
        CommandeAchat commande = getCommandeById(id);

        commande.setDate(commandeDetails.getDate());
        commande.setStatut(commandeDetails.getStatut());
        commande.setMontant(calculerMontantTotal(commandeDetails.getLignes()));

        return commandeAchatRepository.save(commande);
    }

    /**
     * Supprime une commande
     */
    public void deleteCommande(Long id) {
        CommandeAchat commande = getCommandeById(id);
        commandeAchatRepository.delete(commande);
    }

    // Custom Business Methods

    /**
     * Trouve les commandes d'un fournisseur
     */
    public List<CommandeAchat> getCommandesByFournisseur(Long fournisseurId) {
        return commandeAchatRepository.findByFournisseurId(fournisseurId);
    }

    /**
     * Trouve les commandes par statut
     */
    public List<CommandeAchat> getCommandesByStatut(String statut) {
        return commandeAchatRepository.findByStatut(statut);
    }

    /**
     * Trouve les commandes entre deux dates
     */
    public List<CommandeAchat> getCommandesBetweenDates(LocalDate startDate, LocalDate endDate) {
        return commandeAchatRepository.findByDateBetween(startDate, endDate);
    }

    /**
     * Change le statut d'une commande
     */
    public CommandeAchat changeStatutCommande(Long id, String nouveauStatut) {
        CommandeAchat commande = getCommandeById(id);
        commande.setStatut(nouveauStatut);
        return commandeAchatRepository.save(commande);
    }

 

    /**
     * Calcule le montant total d'une commande
     */
    private Double calculerMontantTotal(List<LigneCommandeAchat> lignes) {
        if (lignes == null || lignes.isEmpty()) {
            return 0.0;
        }
        return lignes.stream()
                .mapToDouble(ligne -> ligne.getQuantite() * ligne.getPrixUnitaire())
                .sum();
    }

    /**
     * Valide une commande (passe le statut à "LIVREE")
     */
    public CommandeAchat validerCommande(Long id) {
        CommandeAchat commande = getCommandeById(id);
        if (!commande.getStatut().equals("EN_COURS")) {
            throw new IllegalStateException("Seules les commandes EN_COURS peuvent être validées");
        }
        commande.setStatut("LIVREE");
        return commandeAchatRepository.save(commande);
    }
}