package tn.gest.fourni.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class CommandeAchat {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;
    
    private LocalDate dateLivraison; // 
    private LocalDate date;
    private String statut; // "EN_COURS", "LIVREE", "ANNULEE"
    private Double montant;
    
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JsonManagedReference

    private List<LigneCommandeAchat> lignes;
    
    
}
