package tn.gest.fourni.service;

import tn.gest.fourni.models.CommandeAchat;
import tn.gest.fourni.models.Fournisseur;
import tn.gest.fourni.models.HistoriqueAchats;
import tn.gest.fourni.models.LigneCommandeAchat;
import tn.gest.fourni.repository.CommandeAchatRepository;
import tn.gest.fourni.repository.FournisseurRepository;
import tn.gest.fourni.repository.HistoriqueAchatsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class CommandeAchatService {

    @Autowired
    private CommandeAchatRepository commandeAchatRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;
    
    @Autowired
    private HistoriqueAchatsRepository historiqueAchatsRepository;


    // ➕ Créer une nouvelle commande
    public CommandeAchat createCommande(CommandeAchat commande, Long fournisseurId) {
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID : " + fournisseurId));

        commande.setFournisseur(fournisseur);
        commande.setDate(LocalDate.now());
        commande.setStatut("EN_COURS");

        if (commande.getLignes() != null) {
            commande.getLignes().forEach(ligne -> ligne.setCommande(commande));
        }

        commande.setMontant(calculerMontantTotal(commande.getLignes()));
        
        // ➕ Vérification et ajout de la date de livraison
        if (commande.getDateLivraison() == null) {
            throw new RuntimeException("La date de livraison est obligatoire.");
        } else if (commande.getDateLivraison().isBefore(LocalDate.now())) {
            throw new RuntimeException("La date de livraison ne peut pas être dans le passé.");
        }
        return commandeAchatRepository.save(commande);
    }

    // 🔄 Mise à jour d'une commande
    public CommandeAchat updateCommande(Long id, CommandeAchat commandeDetails) {
        CommandeAchat commande = getCommandeById(id);

        commande.setDate(commandeDetails.getDate());
        commande.setStatut(commandeDetails.getStatut());

        // ⚠️ Suppression des anciennes lignes si nécessaire
        commande.getLignes().clear();
        if (commandeDetails.getLignes() != null) {
            commandeDetails.getLignes().forEach(ligne -> {
                ligne.setCommande(commande); // pour la relation bidirectionnelle
                commande.getLignes().add(ligne);
            });
        }

        commande.setMontant(calculerMontantTotal(commande.getLignes()));
        return commandeAchatRepository.save(commande);
    }

    // 📄 Récupérer toutes les commandes
    public List<CommandeAchat> getAllCommandes() {
        return commandeAchatRepository.findAll();
    }

    // 🔍 Récupérer une commande par son ID
    public CommandeAchat getCommandeById(Long id) {
        return commandeAchatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID : " + id));
    }

    // ❌ Supprimer une commande
    public void deleteCommande(Long id) {
        CommandeAchat commande = getCommandeById(id);
        commandeAchatRepository.delete(commande);
    }

    // 📌 Commandes d’un fournisseur
    public List<CommandeAchat> getCommandesByFournisseur(Long fournisseurId) {
        return commandeAchatRepository.findByFournisseurId(fournisseurId);
    }

    // 📌 Commandes par statut
    public List<CommandeAchat> getCommandesByStatut(String statut) {
        return commandeAchatRepository.findByStatut(statut);
    }

    // 📆 Commandes entre deux dates
    public List<CommandeAchat> getCommandesBetweenDates(LocalDate startDate, LocalDate endDate) {
        return commandeAchatRepository.findByDateBetween(startDate, endDate);
    }

    // ✅ Valider une commande
    public CommandeAchat validerCommande(Long id) {
        CommandeAchat commande = getCommandeById(id);

        if (!commande.getStatut().equals("EN_COURS")) {
            throw new IllegalStateException("Seules les commandes EN_COURS peuvent être validées");
        }
        commande.setDateLivraison(LocalDate.now());

        long delai = ChronoUnit.DAYS.between(commande.getDate(), commande.getDateLivraison());


        // Pour chaque ligne de commande, créer un enregistrement dans l'historique
        for (LigneCommandeAchat ligne : commande.getLignes()) {
            HistoriqueAchats historique = new HistoriqueAchats();
            historique.setFournisseur(commande.getFournisseur());
            historique.setProduit(ligne.getProduit());
            historique.setQuantite(ligne.getQuantite());

            // Délai fictif ici (par exemple, on suppose que la commande est livrée 5 jours après)
            historique.setDelaiLivraison((int) delai);  // Tu peux changer cette logique selon tes besoins

            historiqueAchatsRepository.save(historique);
        }

        commande.setStatut("LIVREE");
        return commandeAchatRepository.save(commande);
    }


    // 🔄 Changer statut manuellement
    public CommandeAchat changeStatutCommande(Long id, String nouveauStatut) {
        CommandeAchat commande = getCommandeById(id);
        commande.setStatut(nouveauStatut);
        return commandeAchatRepository.save(commande);
    }

    // 🧮 Calcul du montant total
    private Double calculerMontantTotal(List<LigneCommandeAchat> lignes) {
        if (lignes == null || lignes.isEmpty()) {
            return 0.0;
        }
        return lignes.stream()
                .mapToDouble(ligne -> ligne.getQuantite() * ligne.getPrixUnitaire())
                .sum();
    }
}
