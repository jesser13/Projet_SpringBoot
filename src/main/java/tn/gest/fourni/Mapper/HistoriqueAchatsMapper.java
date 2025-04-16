package tn.gest.fourni.Mapper;

import tn.gest.fourni.DTO.HistoriqueAchatsDTO;
import tn.gest.fourni.models.HistoriqueAchats;
import tn.gest.fourni.models.Fournisseur;

public class HistoriqueAchatsMapper {

    public static HistoriqueAchatsDTO toDTO(HistoriqueAchats historique) {
        HistoriqueAchatsDTO dto = new HistoriqueAchatsDTO();
        dto.setId(historique.getId());
        dto.setProduit(historique.getProduit());
        dto.setQuantite(historique.getQuantite());
        dto.setDelaiLivraison(historique.getDelaiLivraison());
        
        if (historique.getFournisseur() != null) {
            dto.setFournisseurId(historique.getFournisseur().getId());
            dto.setFournisseurNom(historique.getFournisseur().getNom()); // suppose que Fournisseur a un champ nom
        }

        return dto;
    }

    public static HistoriqueAchats toEntity(HistoriqueAchatsDTO dto, Fournisseur fournisseur) {
        HistoriqueAchats historique = new HistoriqueAchats();
        historique.setId(dto.getId());
        historique.setProduit(dto.getProduit());
        historique.setQuantite(dto.getQuantite());
        historique.setDelaiLivraison(dto.getDelaiLivraison());
        historique.setFournisseur(fournisseur); // fourni par le service
        return historique;
    }
}
