package tn.gest.fourni.DTO;

import lombok.Data;

@Data
public class LigneCommandeAchatDTO {
    private Long id;
    private String produit;
    private Integer quantite;
    private Double prixUnitaire;
}
