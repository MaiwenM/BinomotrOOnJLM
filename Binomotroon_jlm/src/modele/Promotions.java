package modele;

import java.time.LocalDate;
import java.util.Date;

public class Promotions {
    protected int id;
    protected String nom;
    protected Date date_demarrage;
    protected Date date_fin;


    public Promotions(int id, String nom, Date date_demarrage, Date date_fin) {
        this.id = id;
        this.nom = nom;
        this.date_demarrage = date_demarrage;
        this.date_fin = date_fin;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate_demarrage() {
        return date_demarrage;
    }

    public void setDate_demarrage(Date date_demarrage) {
        this.date_demarrage = date_demarrage;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }
}
