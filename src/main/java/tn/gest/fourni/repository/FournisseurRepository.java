package tn.gest.fourni.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.gest.fourni.models.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
	
	List<Fournisseur> findByNom(String nom);
	
    // Trouve les fournisseurs par qualité de service

    List<Fournisseur> findByQualiteService(String qualiteService);
    
    // Trouve les fournisseurs triés par note décroissante
    List<Fournisseur> findByOrderByNoteDesc();
    

}
