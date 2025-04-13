package tn.gest.fourni.service;

import tn.gest.fourni.models.Fournisseur;
import tn.gest.fourni.repository.FournisseurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FournisseurService {

    private final FournisseurRepository fournisseurRepository;

    public FournisseurService(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    // CRUD Operations
    
    /**
     * Crée un nouveau fournisseur
     */
    public Fournisseur createFournisseur(Fournisseur fournisseur) {
        if (fournisseur.getNote() == null) {
            fournisseur.setNote(3.0); // Note par défaut
        }
        return fournisseurRepository.save(fournisseur);
    }

    /**
     * Récupère tous les fournisseurs
     */
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    /**
     * Récupère un fournisseur par son ID
     */
    public Fournisseur getFournisseurById(Long id) {
        return fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID : " + id));
    }

    /**
     * Met à jour un fournisseur existant
     */
    public Fournisseur updateFournisseur(Long id, Fournisseur fournisseurDetails) {
        Fournisseur fournisseur = getFournisseurById(id);
        
        fournisseur.setNom(fournisseurDetails.getNom());
        fournisseur.setContact(fournisseurDetails.getContact());
        fournisseur.setQualiteService(fournisseurDetails.getQualiteService());
        fournisseur.setNote(fournisseurDetails.getNote());
        
        return fournisseurRepository.save(fournisseur);
    }

    /**
     * Supprime un fournisseur
     */
    public void deleteFournisseur(Long id) {
        Fournisseur fournisseur = getFournisseurById(id);
        fournisseurRepository.delete(fournisseur);
    }

    // Custom Business Methods
    
    /**
     * Trouve les fournisseurs par nom
     */
    public List<Fournisseur> findFournisseursByNom(String nom) {
        return fournisseurRepository.findByNom(nom);
    }

    /**
     * Trouve les fournisseurs par qualité de service
     */
    public List<Fournisseur> findFournisseursByQualiteService(String qualiteService) {
        return fournisseurRepository.findByQualiteService(qualiteService);
    }

    /**
     * Récupère les fournisseurs triés par note décroissante
     */
    public List<Fournisseur> getFournisseursByNoteDesc() {
        return fournisseurRepository.findByOrderByNoteDesc();
    }

    /**
     * Évalue un fournisseur et met à jour sa note
     */
    public Fournisseur evaluerFournisseur(Long id, Double nouvelleNote) {
        if (nouvelleNote < 0 || nouvelleNote > 5) {
            throw new IllegalArgumentException("La note doit être comprise entre 0 et 5");
        }

        Fournisseur fournisseur = getFournisseurById(id);
        
        // Calcul de la nouvelle note (moyenne entre ancienne et nouvelle note)
        double noteMiseAJour = (fournisseur.getNote() + nouvelleNote) / 2;
        fournisseur.setNote(noteMiseAJour);
        
        return fournisseurRepository.save(fournisseur);
    }

    /**
     * Récupère les fournisseurs avec une note supérieure ou égale à la valeur spécifiée
     */
    public List<Fournisseur> getFournisseursWithNoteAbove(Double noteMinimale) {
        return fournisseurRepository.findByOrderByNoteDesc()
                .stream()
                .filter(f -> f.getNote() >= noteMinimale)
                .toList();
    }
}