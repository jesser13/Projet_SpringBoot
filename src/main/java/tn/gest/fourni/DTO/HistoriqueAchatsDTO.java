package tn.gest.fourni.DTO;

import lombok.Data;

@Data
public class HistoriqueAchatsDTO {
    private Long id;
    private Long fournisseurId;
    private String fournisseurNom;
    private String produit;
    private Integer quantite;
    private Integer delaiLivraison;
}
