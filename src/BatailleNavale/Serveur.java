package BatailleNavale;

import java.net.*;

public class Serveur {
    /*
     * @TODO:
     * plusieures parties peuvent être réalisés en même temps
     */

    public static void main(String[] args) {
        try {
            // Création et lancement du serveur
            System.out.println("Lancement du serveur....");
            ServerSocket ecoute = new ServerSocket(1234);
            System.out.println("Serveur lancé avec succès!");

            // écoute pour des joueurs
            int idMatch = 0;
            Socket joueur1 = ecoute.accept();
            // new ThreadInit(idMatch, joueur1).start();
            Socket joueur2 = ecoute.accept();
            // new ThreadInit(idMatch, joueur2).start();
            new ThreadMatch(idMatch, joueur1, joueur2).start();
            ecoute.close();
        } catch (Exception e) {
            System.out.println("Erreur Inconnu (Réf: " + e + "). Arrêt du Serveur");
        }
    }
}