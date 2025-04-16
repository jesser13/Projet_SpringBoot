package tn.gest.fourni.Mapper;

import org.springframework.stereotype.Component;
import tn.gest.fourni.DTO.LigneCommandeAchatDTO;
import tn.gest.fourni.models.LigneCommandeAchat;

@Component
public class LigneCommandeAchatMapper {

    public LigneCommandeAchatDTO toDTO(LigneCommandeAchat entity) {
        LigneCommandeAchatDTO dto = new LigneCommandeAchatDTO();
        dto.setId(entity.getId());
        dto.setProduit(entity.getProduit());
        dto.setQuantite(entity.getQuantite());
        dto.setPrixUnitaire(entity.getPrixUnitaire());
        return dto;
    }

    public LigneCommandeAchat toEntity(LigneCommandeAchatDTO dto) {
        LigneCommandeAchat entity = new LigneCommandeAchat();
        entity.setId(dto.getId());
        entity.setProduit(dto.getProduit());
        entity.setQuantite(dto.getQuantite());
        entity.setPrixUnitaire(dto.getPrixUnitaire());
        return entity;
    }
}
