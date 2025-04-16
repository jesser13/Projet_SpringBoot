package tn.gest.fourni.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CommandeAchatDTO {
    private Long id;
    private Long fournisseurId;
    private LocalDate date;
    private String statut;
    private Double montant;
    private List<LigneCommandeAchatDTO> lignes;
}
