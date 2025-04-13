package tn.gest.fourni.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class HistoriqueAchats {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;
    
    private String produit;
    private Integer quantite;
    private Integer delaiLivraison; // en jours
    
}