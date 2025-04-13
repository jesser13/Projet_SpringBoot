package tn.gest.fourni.service;

import tn.gest.fourni.models.CommandeAchat;
import tn.gest.fourni.models.LigneCommandeAchat;
import tn.gest.fourni.repository.CommandeAchatRepository;
import tn.gest.fourni.repository.LigneCommandeAchatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LigneCommandeAchatService {

    private final LigneCommandeAchatRepository ligneCommandeAchatRepository;
    private final CommandeAchatRepository commandeAchatRepository;

    public LigneCommandeAchatService(LigneCommandeAchatRepository ligneCommandeAchatRepository,
                                   CommandeAchatRepository commandeAchatRepository) {
        this.ligneCommandeAchatRepository = ligneCommandeAchatRepository;
        this.commandeAchatRepository = commandeAchatRepository;
    }

    // CRUD Operations

    /**
     * Crée une nouvelle ligne de commande
     */
    public LigneCommandeAchat createLigneCommande(LigneCommandeAchat ligneCommande, Long commandeId) {
        CommandeAchat commande = commandeAchatRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID : " + commandeId));
        
        ligneCommande.setCommande(commande);
        LigneCommandeAchat savedLigne = ligneCommandeAchatRepository.save(ligneCommande);
        
        // Mise à jour du montant total de la commande
        updateCommandeTotal(commandeId);
        
        return savedLigne;
    }

    /**
     * Récupère toutes les lignes de commande
     */
    public List<LigneCommandeAchat> getAllLigneCommandes() {
        return ligneCommandeAchatRepository.findAll();
    }

    /**
     * Récupère une ligne de commande par son ID
     */
    public LigneCommandeAchat getLigneCommandeById(Long id) {
        return ligneCommandeAchatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ligne de commande non trouvée avec l'ID : " + id));
    }

    /**
     * Met à jour une ligne de commande existante
     */
    public LigneCommandeAchat updateLigneCommande(Long id, LigneCommandeAchat ligneDetails) {
        validateLigneCommande(ligneDetails); // Validation ajoutée ici
        
        LigneCommandeAchat ligne = getLigneCommandeById(id);
        ligne.setProduit(ligneDetails.getProduit());
        ligne.setQuantite(ligneDetails.getQuantite());
        ligne.setPrixUnitaire(ligneDetails.getPrixUnitaire());
        
        LigneCommandeAchat updatedLigne = ligneCommandeAchatRepository.save(ligne);
        updateCommandeTotal(ligne.getCommande().getId());
        return updatedLigne;
    }
    /**
     * Supprime une ligne de commande
     */
    public void deleteLigneCommande(Long id) {
        LigneCommandeAchat ligne = getLigneCommandeById(id);
        Long commandeId = ligne.getCommande().getId();
        ligneCommandeAchatRepository.delete(ligne);
        
        // Mise à jour du montant total de la commande parente
        updateCommandeTotal(commandeId);
    }

    // Custom Business Methods

    /**
     * Trouve les lignes d'une commande spécifique
     */
    public List<LigneCommandeAchat> getLignesByCommande(Long commandeId) {
        return ligneCommandeAchatRepository.findByCommandeId(commandeId);
    }

    /**
     * Trouve les lignes pour un produit spécifique
     */
    public List<LigneCommandeAchat> getLignesByProduit(String produit) {
        return ligneCommandeAchatRepository.findByProduit(produit);
    }

    /**
     * Trouve les lignes avec quantité supérieure à une valeur
     */
    public List<LigneCommandeAchat> getLignesWithQuantiteGreaterThan(Integer quantite) {
        return ligneCommandeAchatRepository.findByQuantiteGreaterThan(quantite);
    }

    /**
     * Trouve les lignes avec prix unitaire dans une plage
     */
    public List<LigneCommandeAchat> getLignesWithPrixBetween(Double min, Double max) {
        return ligneCommandeAchatRepository.findByPrixUnitaireBetween(min, max);
    }

    /**
     * Calcule le montant total d'une commande
     */
    public Double calculateMontantTotalCommande(Long commandeId) {
        return ligneCommandeAchatRepository.calculateTotalAmountByCommandeId(commandeId);
    }

    /**
     * Calcule le montant d'une ligne de commande
     */
    public Double calculateMontantLigne(Long ligneId) {
        LigneCommandeAchat ligne = getLigneCommandeById(ligneId);
        return ligne.getQuantite() * ligne.getPrixUnitaire();
    }

    // Private Helper Methods

    /**
     * Met à jour le montant total de la commande
     */
    private void updateCommandeTotal(Long commandeId) {
        Double total = calculateMontantTotalCommande(commandeId);
        CommandeAchat commande = commandeAchatRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setMontant(total != null ? total : 0.0);
        commandeAchatRepository.save(commande);
    }
    
    /**
     * Valide les données d'une ligne de commande
     * @throws IllegalArgumentException si validation échoue
     */
    private void validateLigneCommande(LigneCommandeAchat ligne) {
        if (ligne == null) {
            throw new IllegalArgumentException("La ligne de commande ne peut pas être null");
        }
        if (ligne.getQuantite() == null || ligne.getQuantite() <= 0) {
            throw new IllegalArgumentException("La quantité doit être un nombre positif");
        }
        if (ligne.getPrixUnitaire() == null || ligne.getPrixUnitaire() <= 0) {
            throw new IllegalArgumentException("Le prix unitaire doit être un nombre positif");
        }
        if (ligne.getProduit() == null || ligne.getProduit().trim().isEmpty()) {
            throw new IllegalArgumentException("Le produit doit être spécifié");
        }
    }


   
    
}