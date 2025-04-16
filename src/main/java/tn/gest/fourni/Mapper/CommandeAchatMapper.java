package tn.gest.fourni.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tn.gest.fourni.DTO.CommandeAchatDTO;
import tn.gest.fourni.models.CommandeAchat;
import tn.gest.fourni.models.Fournisseur;
import tn.gest.fourni.models.LigneCommandeAchat;
import tn.gest.fourni.repository.FournisseurRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommandeAchatMapper {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private LigneCommandeAchatMapper ligneMapper;

    public CommandeAchatDTO toDTO(CommandeAchat entity) {
        CommandeAchatDTO dto = new CommandeAchatDTO();
        dto.setId(entity.getId());
        dto.setFournisseurId(entity.getFournisseur().getId());
        dto.setDate(entity.getDate());
        dto.setStatut(entity.getStatut());
        dto.setMontant(entity.getMontant());

        if (entity.getLignes() != null) {
            dto.setLignes(entity.getLignes().stream()
                    .map(ligneMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public CommandeAchat toEntity(CommandeAchatDTO dto) {
        CommandeAchat entity = new CommandeAchat();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setStatut(dto.getStatut());
        entity.setMontant(dto.getMontant());

        Fournisseur fournisseur = fournisseurRepository.findById(dto.getFournisseurId())
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec ID : " + dto.getFournisseurId()));
        entity.setFournisseur(fournisseur);

        if (dto.getLignes() != null) {
            List<LigneCommandeAchat> lignes = dto.getLignes().stream()
                    .map(ligneMapper::toEntity)
                    .collect(Collectors.toList());

            // Relation bidirectionnelle
            lignes.forEach(l -> l.setCommande(entity));
            entity.setLignes(lignes);
        }

        return entity;
    }
}
