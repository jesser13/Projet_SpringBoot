package tn.gest.fourni.service;

import tn.gest.fourni.models.CommandeAchat;
import tn.gest.fourni.models.LigneCommandeAchat;
import tn.gest.fourni.repository.CommandeAchatRepository;
import tn.gest.fourni.repository.LigneCommandeAchatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LigneCommandeAchatService {

    @Autowired
    private LigneCommandeAchatRepository ligneCommandeAchatRepository;

    @Autowired
    private CommandeAchatRepository commandeAchatRepository;

    // CRUD Operations

    /**
     * Crée une nouvelle ligne de commande
     */
    public LigneCommandeAchat createLigneCommande(LigneCommandeAchat ligneCommande, Long commandeId) {
        validateLigneCommande(ligneCommande);

        CommandeAchat commande = commandeAchatRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID : " + commandeId));

        ligneCommande.setCommande(commande);
        LigneCommandeAchat savedLigne = ligneCommandeAchatRepository.save(ligneCommande);

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
        validateLigneCommande(ligneDetails);

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

        updateCommandeTotal(commandeId);
    }

    // Business Methods

    public List<LigneCommandeAchat> getLignesByCommande(Long commandeId) {
        return ligneCommandeAchatRepository.findByCommandeId(commandeId);
    }

    public List<LigneCommandeAchat> getLignesByProduit(String produit) {
        return ligneCommandeAchatRepository.findByProduit(produit);
    }

    public List<LigneCommandeAchat> getLignesWithQuantiteGreaterThan(Integer quantite) {
        return ligneCommandeAchatRepository.findByQuantiteGreaterThan(quantite);
    }

    public List<LigneCommandeAchat> getLignesWithPrixBetween(Double min, Double max) {
        return ligneCommandeAchatRepository.findByPrixUnitaireBetween(min, max);
    }

    public Double calculateMontantTotalCommande(Long commandeId) {
        return ligneCommandeAchatRepository.calculateTotalAmountByCommandeId(commandeId);
    }

    public Double calculateMontantLigne(Long ligneId) {
        LigneCommandeAchat ligne = getLigneCommandeById(ligneId);
        return ligne.getQuantite() * ligne.getPrixUnitaire();
    }

    // Helpers

    private void updateCommandeTotal(Long commandeId) {
        Double total = calculateMontantTotalCommande(commandeId);
        CommandeAchat commande = commandeAchatRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setMontant(total != null ? total : 0.0);
        commandeAchatRepository.save(commande);
    }

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
