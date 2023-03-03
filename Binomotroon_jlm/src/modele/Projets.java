package modele;

import java.util.ArrayList;

public class Projets {
    protected int id;
    protected String libelle;
    protected String Description;
    protected int id_professeur;


    public Projets(int id, String libelle, String description, int id_professeur) {
        this.id = id;
        this.libelle = libelle;
        Description = description;
        this.id_professeur = id_professeur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getId_professeur() {
        return id_professeur;
    }

    public void setId_professeur(int id_professeur) {
        this.id_professeur = id_professeur;
    }



}
