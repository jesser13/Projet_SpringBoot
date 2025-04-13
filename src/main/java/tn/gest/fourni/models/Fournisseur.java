package tn.gest.fourni.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Fournisseur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String contact;
    private String qualiteService;
    private Double note;
    
    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL)
    private List<CommandeAchat> commandes;
    
    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL)
    private List<HistoriqueAchats> historique;

	
    
    
}
