package tn.gest.fourni.service;

import tn.gest.fourni.models.Fournisseur;
import tn.gest.fourni.models.HistoriqueAchats;
import tn.gest.fourni.repository.FournisseurRepository;
import tn.gest.fourni.repository.HistoriqueAchatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HistoriqueAchatsService {

    private final HistoriqueAchatsRepository historiqueAchatsRepository;
    private final FournisseurRepository fournisseurRepository;

    public HistoriqueAchatsService(HistoriqueAchatsRepository historiqueAchatsRepository,
                                 FournisseurRepository fournisseurRepository) {
        this.historiqueAchatsRepository = historiqueAchatsRepository;
        this.fournisseurRepository = fournisseurRepository;
    }

    // CRUD Operations

    /**
     * Crée un nouvel historique d'achat
     */
    public HistoriqueAchats createHistorique(HistoriqueAchats historique, Long fournisseurId) {
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID : " + fournisseurId));
        
        historique.setFournisseur(fournisseur);
        return historiqueAchatsRepository.save(historique);
    }

    /**
     * Récupère tous les historiques d'achat
     */
    public List<HistoriqueAchats> getAllHistoriques() {
        return historiqueAchatsRepository.findAll();
    }

    /**
     * Récupère un historique par son ID
     */
    public HistoriqueAchats getHistoriqueById(Long id) {
        return historiqueAchatsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historique non trouvé avec l'ID : " + id));
    }

    /**
     * Met à jour un historique existant
     */
    public HistoriqueAchats updateHistorique(Long id, HistoriqueAchats historiqueDetails) {
        HistoriqueAchats historique = getHistoriqueById(id);
        
        historique.setProduit(historiqueDetails.getProduit());
        historique.setQuantite(historiqueDetails.getQuantite());
        historique.setDelaiLivraison(historiqueDetails.getDelaiLivraison());
        
        return historiqueAchatsRepository.save(historique);
    }

    /**
     * Supprime un historique
     */
    public void deleteHistorique(Long id) {
        HistoriqueAchats historique = getHistoriqueById(id);
        historiqueAchatsRepository.delete(historique);
    }

    // Custom Business Methods

    /**
     * Trouve l'historique d'un fournisseur
     */
    public List<HistoriqueAchats> getHistoriqueByFournisseur(Long fournisseurId) {
        return historiqueAchatsRepository.findByFournisseurId(fournisseurId);
    }

    /**
     * Trouve l'historique pour un produit spécifique
     */
    public List<HistoriqueAchats> getHistoriqueByProduit(String produit) {
        return historiqueAchatsRepository.findByProduit(produit);
    }

    /**
     * Calcule la moyenne du délai de livraison pour un fournisseur
     */
    public Double getMoyenneDelaiLivraison(Long fournisseurId) {
        Double moyenne = historiqueAchatsRepository.findMoyenneDelaiLivraisonByFournisseurId(fournisseurId);
        return moyenne != null ? moyenne : 0.0;
    }

    /**
     * Enregistre une nouvelle livraison dans l'historique
     */
    public HistoriqueAchats enregistrerLivraison(Long fournisseurId, String produit, 
                                               Integer quantite, Integer delaiLivraison) {
        HistoriqueAchats historique = new HistoriqueAchats();
        historique.setProduit(produit);
        historique.setQuantite(quantite);
        historique.setDelaiLivraison(delaiLivraison);
        
        return createHistorique(historique, fournisseurId);
    }

    /**
     * Calcule la performance moyenne d'un fournisseur
     * (Plus le score est élevé, meilleur est le fournisseur)
     */
    public Double calculerPerformanceFournisseur(Long fournisseurId) {
        Double moyenneDelai = getMoyenneDelaiLivraison(fournisseurId);
        if (moyenneDelai == null || moyenneDelai == 0) {
            return 0.0;
        }
        // Performance inversement proportionnelle au délai
        return 100.0 / moyenneDelai;
    }

    /**
     * Trouve les produits les plus commandés
     */
    public List<Object[]> getProduitsPlusCommandes() {
        return historiqueAchatsRepository.findTopProduitsCommandes();
    }
}