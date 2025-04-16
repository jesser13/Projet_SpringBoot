package tn.gest.fourni.Mapper;

import org.springframework.stereotype.Component;

import tn.gest.fourni.DTO.FournisseurDTO;
import tn.gest.fourni.models.Fournisseur;

@Component
public class FournisseurMapper {

    public FournisseurDTO toDTO(Fournisseur fournisseur) {
        FournisseurDTO dto = new FournisseurDTO();
        dto.setId(fournisseur.getId());
        dto.setNom(fournisseur.getNom());
        dto.setContact(fournisseur.getContact());
        dto.setQualiteService(fournisseur.getQualiteService());
        dto.setNote(fournisseur.getNote());
        return dto;
    }

    public Fournisseur toEntity(FournisseurDTO dto) {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(dto.getId());
        fournisseur.setNom(dto.getNom());
        fournisseur.setContact(dto.getContact());
        fournisseur.setQualiteService(dto.getQualiteService());
        fournisseur.setNote(dto.getNote());
        return fournisseur;
    }
}

