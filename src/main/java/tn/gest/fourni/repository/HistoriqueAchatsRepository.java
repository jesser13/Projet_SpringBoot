package tn.gest.fourni.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.gest.fourni.models.HistoriqueAchats;

public interface HistoriqueAchatsRepository extends JpaRepository<HistoriqueAchats, Long> {
	
    // Trouve l'historique d'un fournisseur
    List<HistoriqueAchats> findByFournisseurId(Long fournisseurId);

    // Trouve l'historique pour un produit spécifique
    List<HistoriqueAchats> findByProduit(String produit);
    
    // Calcul de la moyenne du délai de livraison (requête personnalisée)
    @Query("SELECT AVG(h.delaiLivraison) FROM HistoriqueAchats h WHERE h.fournisseur.id = :fournisseurId")
    Double findMoyenneDelaiLivraisonByFournisseurId(Long fournisseurId);

 // Ajoutez ces méthodes dans HistoriqueAchatsRepository.java

    @Query("SELECT h.produit, SUM(h.quantite) as totalQuantite " +
           "FROM HistoriqueAchats h " +
           "GROUP BY h.produit " +
           "ORDER BY totalQuantite DESC")
    List<Object[]> findTopProduitsCommandes();

    @Query("SELECT h.fournisseur, AVG(h.delaiLivraison) as delaiMoyen " +
           "FROM HistoriqueAchats h " +
           "GROUP BY h.fournisseur " +
           "ORDER BY delaiMoyen ASC")
    List<Object[]> findFournisseursByDelaiLivraison();


}
