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
            int idMatch = 1;
            Socket joueur1, joueur2;
            // jusqu'à 5 matches
            while (idMatch < 5) {
                joueur1 = ecoute.accept();
                joueur2 = ecoute.accept();
                System.out.println("Two new users have joined! Starting new game...");
                new ThreadMatch(idMatch, joueur1, joueur2).start();
                idMatch++;
            }
            ecoute.close();
        } catch (Exception e) {
            System.out.println("Erreur Inconnu (Réf: " + e + "). Arrêt du Serveur");
        }
    }
}