package modele;

public class Apprenants extends Utilisateurs {
    protected int id_promotion;

    public Apprenants(int id, String prenom, String nom, String mail, String mdp, int id_profil, int id_promotion) {
        super(id, prenom, nom, mail, mdp, id_profil);
        this.id_promotion = id_promotion;
    }

    public int getId_promotion() {
        return id_promotion;
    }

    public void setId_promotion(int id_promotion) {
        this.id_promotion = id_promotion;
    }
}
