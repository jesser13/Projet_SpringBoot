package tn.gest.fourni.service;

import tn.gest.fourni.models.Fournisseur;
import tn.gest.fourni.models.HistoriqueAchats;
import tn.gest.fourni.repository.FournisseurRepository;
import tn.gest.fourni.repository.HistoriqueAchatsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HistoriqueAchatsService {

	@Autowired
	private  HistoriqueAchatsRepository historiqueAchatsRepository;
    private  FournisseurRepository fournisseurRepository;

    
   

    // CRUD Operations

    public HistoriqueAchats createHistorique(HistoriqueAchats historique, Long fournisseurId) {
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID : " + fournisseurId));

        historique.setFournisseur(fournisseur);
        return historiqueAchatsRepository.save(historique);
    }

    public List<HistoriqueAchats> getAllHistoriques() {
        return historiqueAchatsRepository.findAll();
    }

    public HistoriqueAchats getHistoriqueById(Long id) {
        return historiqueAchatsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historique non trouvé avec l'ID : " + id));
    }

    public HistoriqueAchats updateHistorique(Long id, HistoriqueAchats historiqueDetails) {
        HistoriqueAchats historique = getHistoriqueById(id);

        historique.setProduit(historiqueDetails.getProduit());
        historique.setQuantite(historiqueDetails.getQuantite());
        historique.setDelaiLivraison(historiqueDetails.getDelaiLivraison());

        return historiqueAchatsRepository.save(historique);
    }

    public void deleteHistorique(Long id) {
        HistoriqueAchats historique = getHistoriqueById(id);
        historiqueAchatsRepository.delete(historique);
    }

    // Business Logic

    public List<HistoriqueAchats> getHistoriqueByFournisseur(Long fournisseurId) {
        return historiqueAchatsRepository.findByFournisseurId(fournisseurId);
    }

    public List<HistoriqueAchats> getHistoriqueByProduit(String produit) {
        return historiqueAchatsRepository.findByProduit(produit);
    }

    public Double getMoyenneDelaiLivraison(Long fournisseurId) {
        Double moyenne = historiqueAchatsRepository.findMoyenneDelaiLivraisonByFournisseurId(fournisseurId);
        return moyenne != null ? moyenne : 0.0;
    }

    public HistoriqueAchats enregistrerLivraison(Long fournisseurId, String produit,
                                                 Integer quantite, Integer delaiLivraison) {
        HistoriqueAchats historique = new HistoriqueAchats();
        historique.setProduit(produit);
        historique.setQuantite(quantite);
        historique.setDelaiLivraison(delaiLivraison);

        return createHistorique(historique, fournisseurId);
    }

    public Double calculerPerformanceFournisseur(Long fournisseurId) {
        Double moyenneDelai = getMoyenneDelaiLivraison(fournisseurId);
        if (moyenneDelai == null || moyenneDelai == 0) {
            return 0.0;
        }
        return 100.0 / moyenneDelai;
    }

    public List<Object[]> getProduitsPlusCommandes() {
        return historiqueAchatsRepository.findTopProduitsCommandes();
    }
}
