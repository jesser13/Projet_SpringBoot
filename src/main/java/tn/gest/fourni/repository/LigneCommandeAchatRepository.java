package tn.gest.fourni.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.gest.fourni.models.LigneCommandeAchat;

public interface LigneCommandeAchatRepository extends JpaRepository<LigneCommandeAchat, Long> {
	
    // Trouver toutes les lignes d'une commande spécifique
    List<LigneCommandeAchat> findByCommandeId(Long commandeId);
    
    // Trouver les lignes pour un produit spécifique
    List<LigneCommandeAchat> findByProduit(String produit);
    
    // Trouver les lignes avec quantité supérieure à une valeur
    List<LigneCommandeAchat> findByQuantiteGreaterThan(Integer quantite);
    
    // Trouver les lignes avec prix unitaire entre deux valeurs
    List<LigneCommandeAchat> findByPrixUnitaireBetween(Double prixMin, Double prixMax);
    
 // Calculer le montant total des lignes pour une commande
    @Query("SELECT SUM(l.quantite * l.prixUnitaire) FROM LigneCommandeAchat l WHERE l.commande.id = :commandeId")
    Double calculateTotalAmountByCommandeId(Long commandeId);

}
