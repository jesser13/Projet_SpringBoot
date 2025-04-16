package tn.gest.fourni.DTO;

import lombok.Data;

@Data
public class FournisseurDTO {
    private Long id;
    private String nom;
    private String contact;
    private String qualiteService;
    private Double note;
}
