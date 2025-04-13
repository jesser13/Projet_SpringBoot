package tn.gest.fourni.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.gest.fourni.models.CommandeAchat;

public interface CommandeAchatRepository extends JpaRepository<CommandeAchat, Long> {
	
	// Trouve les commandes d'un fournisseur
    List<CommandeAchat> findByFournisseurId(Long fournisseurId);
    
    // Trouve les commandes par statut
    List<CommandeAchat> findByStatut(String statut);

    // Trouve les commandes entre deux dates
    List<CommandeAchat> findByDateBetween(LocalDate startDate, LocalDate endDate);


}
