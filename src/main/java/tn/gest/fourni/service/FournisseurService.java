package tn.gest.fourni.service;

import tn.gest.fourni.DTO.FournisseurDTO;
import tn.gest.fourni.Mapper.FournisseurMapper;
import tn.gest.fourni.models.Fournisseur;
import tn.gest.fourni.repository.FournisseurRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FournisseurService {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private FournisseurMapper fournisseurMapper;

    /**
     * Crée un nouveau fournisseur
     */
    public FournisseurDTO createFournisseur(FournisseurDTO dto) {
        Fournisseur fournisseur = fournisseurMapper.toEntity(dto);
        if (fournisseur.getNote() == null) {
            fournisseur.setNote(3.0); // note par défaut
        }
        return fournisseurMapper.toDTO(fournisseurRepository.save(fournisseur));
    }

    /**
     * Récupère tous les fournisseurs
     */
    public List<FournisseurDTO> getAllFournisseurs() {
        return fournisseurRepository.findAll()
                .stream()
                .map(fournisseurMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un fournisseur par son ID
     */
    public FournisseurDTO getFournisseurById(Long id) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID : " + id));
        return fournisseurMapper.toDTO(fournisseur);
    }

    /**
     * Met à jour un fournisseur
     */
    public FournisseurDTO updateFournisseur(Long id, FournisseurDTO dto) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID : " + id));

        fournisseur.setNom(dto.getNom());
        fournisseur.setContact(dto.getContact());
        fournisseur.setQualiteService(dto.getQualiteService());
        fournisseur.setNote(dto.getNote());

        return fournisseurMapper.toDTO(fournisseurRepository.save(fournisseur));
    }

    /**
     * Supprime un fournisseur
     */
    public void deleteFournisseur(Long id) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID : " + id));
        fournisseurRepository.delete(fournisseur);
    }

    /**
     * Trouve les fournisseurs par nom
     */
    public List<FournisseurDTO> findFournisseursByNom(String nom) {
        return fournisseurRepository.findByNom(nom)
                .stream()
                .map(fournisseurMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Trouve les fournisseurs par qualité de service
     */
    public List<FournisseurDTO> findFournisseursByQualiteService(String qualiteService) {
        return fournisseurRepository.findByQualiteService(qualiteService)
                .stream()
                .map(fournisseurMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les fournisseurs triés par note décroissante
     */
    public List<FournisseurDTO> getFournisseursByNoteDesc() {
        return fournisseurRepository.findByOrderByNoteDesc()
                .stream()
                .map(fournisseurMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Évalue un fournisseur et met à jour sa note
     */
    public FournisseurDTO evaluerFournisseur(Long id, Double nouvelleNote) {
        if (nouvelleNote < 0 || nouvelleNote > 5) {
            throw new IllegalArgumentException("La note doit être comprise entre 0 et 5");
        }

        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID : " + id));

        double noteMiseAJour = (fournisseur.getNote() + nouvelleNote) / 2;
        fournisseur.setNote(noteMiseAJour);

        return fournisseurMapper.toDTO(fournisseurRepository.save(fournisseur));
    }

    /**
     * Récupère les fournisseurs avec une note supérieure ou égale à la valeur spécifiée
     */
    public List<FournisseurDTO> getFournisseursWithNoteAbove(Double noteMinimale) {
        return fournisseurRepository.findByOrderByNoteDesc()
                .stream()
                .filter(f -> f.getNote() >= noteMinimale)
                .map(fournisseurMapper::toDTO)
                .collect(Collectors.toList());
    }
}
