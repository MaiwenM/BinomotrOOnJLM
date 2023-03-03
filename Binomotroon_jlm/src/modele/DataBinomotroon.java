package modele;

import java.sql.*;
import java.util.*;

public class DataBinomotroon {
    static Connection connection = DataAccess.getInstance();

    static Scanner sc = new Scanner(System.in);



    /**Création de la liste des projets avec leur créateur**/
    public ArrayList<String[]> getProjets() {
        ArrayList<String[]> projets = new ArrayList<String[]>();

        String sql = "SELECT p.id, p.libelle, p.description, u.prenom, u.nom FROM projets p JOIN utilisateurs u ON p.id_professeur = u.id";

        try {
            PreparedStatement requete = connection.prepareStatement(sql);
            ResultSet lecture = requete.executeQuery(sql);
            String nom_createur = null;

            while (lecture.next()) {
                String[] projetLu = new String[5];
                projetLu[0] = lecture.getString("id");
                projetLu[1] = lecture.getString("libelle");
                projetLu[2] = lecture.getString("prenom");
                projetLu[3] = lecture.getString("nom");
                projetLu[4] = lecture.getString("description");
                projets.add(projetLu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projets;
    }

    /**Création de la liste des projets auxquels a participé l’utilisateur**/
    public ArrayList<Projets> ListeProjetsApprenant() {

        ArrayList<Projets> projetsA = new ArrayList<>();
        System.out.println("Saisissez votre id utilisateur :");
        int id_utilisateur = sc.nextInt();
        String sql = "SELECT * FROM projets p, utilisateurs u, projets_apprenants p_a WHERE p.id = p_a.id_projet AND p_a.id_apprenant = u.id AND u.id = ?";

        try {
            PreparedStatement requete = connection.prepareStatement(sql);
            requete.setInt(1, id_utilisateur);
            ResultSet lecture = requete.executeQuery();

            while (lecture.next()) {
                Projets projetALu = new Projets(lecture.getInt("p.id"), lecture.getString("libelle"), lecture.getString("description"), lecture.getInt("id_professeur"));
                projetsA.add(projetALu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projetsA;
    }

    /** Création de la liste des groupes qui ont réalisé un projet particulier*/
    public static ArrayList<String[]> getApprenants_Groupes_Projet() {
        ArrayList<String[]> projetsA = new ArrayList<>();
        System.out.println("Sélectionner le projet pour lequel je souhaite consulter les groupes: ");
        int numero_projet = sc.nextInt();

        String sql = "SELECT p_a.nom_equipe, p_a.date_brief, u.nom, u.prenom FROM projets p, utilisateurs u, projets_apprenants p_a WHERE p.id = p_a.id_projet AND p_a.id_apprenant = u.id AND id_projet=? ORDER BY nom_equipe";

        try {
            PreparedStatement requete = connection.prepareStatement(sql);
            requete.setInt(1, numero_projet);
            ResultSet lecture = requete.executeQuery();

            while (lecture.next()) {
                String[] projetALu = new String[4];
                projetALu[0] = lecture.getString("nom_equipe");
                projetALu[1] = lecture.getString("date_brief");
                projetALu[2] = lecture.getString("nom");
                projetALu[3] = lecture.getString("prenom");
                projetsA.add(projetALu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projetsA;
    }

    /** Création de la liste des promotions **/
    public ArrayList<Promotions> getPromotions() {
        ArrayList<Promotions> promotions = new ArrayList<>();

        try {
            Statement requete = DataAccess.getInstance().createStatement();
            String sql = "SELECT * FROM promotions ORDER BY date_demarrage";
            ResultSet lecture = requete.executeQuery(sql);

            while (lecture.next()) {
                Promotions promotionLue = new Promotions(lecture.getInt("id"), lecture.getString("nom"), lecture.getDate("Date_demarrage"), lecture.getDate("Date_fin"));
                promotions.add(promotionLue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
    }

    /** Création de liste des apprenants d'une promotion */
    public  ArrayList<String[]> getApprenants_promotions() {
        ArrayList<String[]> promoA = new ArrayList<String[]>();
        System.out.println("Saisissez promotion:");
        int promotion_choisie = sc.nextInt();
        String sql = "SELECT u.nom, u.prenom FROM promotions prom JOIN apprenants a ON prom.id = a.id_promotion JOIN utilisateurs u ON a.id_utilisateur = u.id WHERE prom.id=?";

        try {
            PreparedStatement requete = connection.prepareStatement(sql);
            requete.setInt(1, promotion_choisie);
            ResultSet lecture = requete.executeQuery();

            while (lecture.next()) {
                String[] promoLue = new String[2];
                promoLue[0] = lecture.getString("nom");
                promoLue[1] = lecture.getString("prenom");
                promoA.add(promoLue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promoA;
    }
  /** Création de la liste des apprenants d'une promotion avant mélange */
    public List getPromoAvantMelange() {
        List<String> liste_apprenants_promo = new ArrayList<String>();
        System.out.println("Saisissez promotion:");
        int promotion_choisie = sc.nextInt();
        String sql = "SELECT u.id, u.prenom, u.nom FROM utilisateurs u JOIN apprenants a ON u.id=a.id_utilisateur JOIN promotions prom ON a.id_promotion=prom.id WHERE prom.id=?";
        try {
            PreparedStatement requete = connection.prepareStatement(sql);
            requete.setInt(1, promotion_choisie);
            ResultSet rs = requete.executeQuery();

            while (rs.next()) {
                liste_apprenants_promo.add(rs.getInt("id") + " " + rs.getString("prenom") + " " + rs.getString("nom"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return liste_apprenants_promo;
    }

    /** Création de la liste mélangée des apprenants d'une promotion */
    public static List getPromoApresMelange() {
        DataBinomotroon db = new DataBinomotroon();
        List<String> liste_apprenants_promo = db.getPromoAvantMelange();
        Collections.shuffle(liste_apprenants_promo);
        return liste_apprenants_promo;
    }

}

