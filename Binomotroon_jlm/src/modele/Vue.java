package modele;

import java.sql.*;
import java.util.*;

public class Vue {

    static Scanner sc = new Scanner(System.in);

    static DataBinomotroon db = new DataBinomotroon();

    /** Identification et connexion de l’utilisateur pour accéder aux choix d'actions en fonction du profil (apprenant ou professeur) **/
    public static void se_connecter() {
        boolean connexionReussie = false;
        while (!connexionReussie) {
            System.out.println("Veuillez saisir votre identifiant (email): ");
            String email_saisi = sc.nextLine();
            System.out.println("Veuillez saisir votre mot de passe: ");
            String mdp_saisi = sc.nextLine();
            Connection connection = DataAccess.getInstance();
            String sql = "SELECT * FROM utilisateurs WHERE mail=? AND mdp=? ";
            try {
                PreparedStatement requete = connection.prepareStatement(sql);
                requete.setString(1, email_saisi);
                requete.setString(2, mdp_saisi);
                ResultSet lecture = requete.executeQuery();
                if (lecture.next()) {
                    int id_profil = lecture.getInt("id_profil");
                    int id_u = lecture.getInt("id");
                    String prenom = lecture.getString("prenom");
                    String nom = lecture.getString("nom");
                    if (id_profil == 1) {
                        System.out.println("Bienvenue " + prenom + " " + nom + " dans notre application BinomotrOOn!");
                        while (true) {
                            System.out.println("");
                            System.out.println("Que souhaitez-vous faire ?");
                            System.out.println("1. Consulter les groupes d'un projet particulier pour voir quels sont les apprenants de chaque groupe");
                            System.out.println("2. Consulter la liste des projets auxquels vous avez participé");
                            System.out.println("3: Quitter");
                            int choix = sc.nextInt();
                            if (choix == 1) {
                                Afficher_projets();
                                Afficher_apprenants_groupes_projet();
                            } else if (choix == 2) {
                                AfficherProjetsApprenant();
                            } else if (choix == 3) {
                                break;
                            } else {
                                System.out.println("Choix non valide, veuillez réessayer.");
                            }
                        }
                    } else if (id_profil == 2) {  // si id_profil == 2, l'utilisateur est un professeur.
                        System.out.println("Bienvenue" + prenom + " " + nom + "dans notre application BinomotrOOn!");
                        while (true) {
                            System.out.println("");
                            System.out.println("Que souhaitez-vous faire ?"); //Menu des choix proposés aux professeurs
                            System.out.println("1. Consulter la liste des projets afin de connaitre leurs informations ");
                            System.out.println("2. Consulter la liste des promotions et de leurs apprenants");
                            System.out.println("3. Créer un nouveau projet");
                            System.out.println("4. Constituer des groupes d'apprenants");
                            System.out.println("5. Quitter");
                            System.out.println("Merci de saisir le numéro de votre requête:");
                            int choix = sc.nextInt();
                            if (choix == 1) {
                                Afficher_projets();
                            } else if (choix == 2) {
                                Afficher_promotions();
                                Afficher_apprenants_promotions();
                            } else if (choix == 3) {
                                creer_projet();
                            } else if (choix == 4) {
                                Créer_groupes();
                            } else if (choix == 5) {
                                break;
                            } else {
                                System.out.println("Choix non valide, veuillez réessayer.");
                            }
                        }
                    }
                    connexionReussie = true;
                } else {
                    System.out.println("Votre identifiant ou votre mot de passe est invalide. Retentez votre chance!");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /** Affichage de la liste des projets récupérée grâce à la méthode getProjets() dans DataBinomotroon **/
    public static void Afficher_projets() {
        ArrayList<String[]> projets = db.getProjets();

        if (projets.isEmpty()) {
            System.out.println("Aucun projet n'a été trouvé");
        } else {
            System.out.println("Liste des projets : ");
            for (String[] projetLu : projets) {
                System.out.println(projetLu[0] + " " +projetLu[1] + ", nom du créateur : " + projetLu[2] + " " + projetLu[3] + ", Description: " + projetLu[4]);

            }
        }
    }

    /** Affichage de la liste des promotions récupérée grâce à la méthode getPromotions() dans DataBinomotroon **/
    public static void Afficher_promotions() {
        ArrayList<Promotions> promotions = db.getPromotions();

        for (Promotions promotionLue : promotions) {
            System.out.println(promotionLue.getId() + ". Nom de la promotion :" + promotionLue.getNom() + ", Date de démarrage: " + (promotionLue.getDate_demarrage()));
        }
    }

    /** Affichage de la liste des apprenants d'une promotion en particulier récupérée grâce à la méthode
     getApprenants_promotions() dans DataBinomotroon **/
    public static void Afficher_apprenants_promotions() {
        ArrayList<String[]> promoA = db.getApprenants_promotions();
        if (promoA.isEmpty()) {
            System.out.println("Aucun projet n'a été trouvé");
        } else {
            System.out.println("Liste des apprenants : ");
            for (String[] promoLue : promoA) {
                System.out.println(promoLue[0] + " " + promoLue[1]);
            }
        }
    }


    //public static void Afficher_liste_Promo_Avant_Melange() {
    //  List Liste_promo_avant_melange = db.getPromoAvantMelange();
    //System.out.println("Liste des apprenants avant mélange: " + Liste_promo_avant_melange + "\n");
    //}


   // public static void Afficher_liste_Promo_Apres_Melange() {
    //    List Liste_promo_apres_melange = db.getPromoApresMelange();
    //  System.out.println("Liste des apprenants après mélange: " + Liste_promo_apres_melange + "\n");
   // }


    /** Création des groupes d'apprenants formés à partir de la liste mélangée des apprenants récupérée grâce
     à getPromoApresMelange dans DataBinomotroon **/
    public static void Créer_groupes() {
        List<String> liste_groupes = new ArrayList<>();
        List<String> liste_apprenants_promo = db.getPromoApresMelange();
        System.out.println("Saisissez le numéro du projet pour lequel vous souhaitez créer des groupes:");
        int id_projet = sc.nextInt();
        System.out.println("Saisissez la taille de groupe souhaitée:");
        int taille_groupe = sc.nextInt();
        int j = 1;
        for (int i = 0; i < liste_apprenants_promo.size(); i += taille_groupe) {
            liste_groupes.add(liste_apprenants_promo.subList(i, Math.min(i + taille_groupe,
                    liste_apprenants_promo.size())).toString());
            System.out.println("Groupe : " + j + (liste_apprenants_promo.subList(i, Math.min(i + taille_groupe,
                    liste_apprenants_promo.size())).toString()) + "\n");
            j++;
        }
    }

    /** Affichage de la liste des projets réalisés par un apprenant grâce à la méthode ListeProjetsApprenant()
     dans DataBinomotroon **/
    public static void AfficherProjetsApprenant() {
        ArrayList<Projets> projetsA = db.ListeProjetsApprenant();

        for (Projets projetLu : projetsA) {
            System.out.println(projetLu.getId() + ". Nom du projet :" + projetLu.getLibelle());
        }
    }

    /** Affichage de la liste des apprenants par groupe d'un projet particulier grâce à la méthode
     getApprenants_Groupes_Projet() dans DataBinomotroon **/
    public static void Afficher_apprenants_groupes_projet() {
        ArrayList<String[]> projetsA = db.getApprenants_Groupes_Projet();

        if (projetsA.isEmpty()) {
            System.out.println("Aucun projet n'a été trouvé");
        } else {
            System.out.println("Liste des groupes du projet choisi : ");
            for (String[] projetALu : projetsA) {
                System.out.println("Projet : " + projetALu[0] + ", date de départ : " + projetALu[1] + ", apprenant : " + projetALu[2] + " " + projetALu[3]);
            }
        }
    }

    /** Création d'un projet par un professeur et insertion du nouveau projet dans la base de données */
    public static void creer_projet() {
        Connection connection = DataAccess.getInstance();
        Scanner entree = new Scanner(System.in);
        System.out.println("Saisissez le nom du projet: ");
        String nom_projet = entree.nextLine();
        System.out.println("Saisissez la description du projet: ");
        String description_projet = entree.nextLine();
        System.out.println("Saisissez votre id: ");
        int id_createur = entree.nextInt();
        String sql = "INSERT INTO projets (libelle, description, id_professeur)  VALUES (?, ?, ?)";
        try {
            PreparedStatement requete = connection.prepareStatement(sql);
            requete.setString(1, nom_projet);
            requete.setString(2, description_projet);
            requete.setInt(3, id_createur);
            int resultat = requete.executeUpdate();
            if (resultat == 1) {
                System.out.println("Le projet a été enregistré avec succès");
            } else {
                System.out.println("Il y a eu une erreur lors de la créationdu projet. ");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}