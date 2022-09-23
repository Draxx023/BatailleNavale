package BatailleNavale;

import java.net.*;

public class Serveur {
    /*
     * Il faudra mettre en place un protocol adapté
     * La squelette du chat peut-être utilisé mais attention aux détails
     * 
     * @TODO:
     * les données du jeu doivent être côté serveur
     * plusieures parties peuvent être réalisés en même temps
     * 
     * @PLUS: chat fonctionnel entre les joueurs
     * 
     * @IDEAS:
     * mainServer class:
     * ServerSocket instance
     * loop for listening to new players joining
     * thread coté serveur qui gère un jeu
     * 
     * créer un serveur avec les données du jeu
     * attendre et écouter l'arrivage des clients
     * créer une salle d'attente quand un joueur entre
     * quand deux joueurs sont en attente, création d'une partie
     * stockage des deux formations et de leur état
     * envoi de trois types d'info: navire adv touché, navire ami touché, rien
     * notification de perte ou victoire et arret du jeu / thread
     * destruction de la partie mais pas des sockets joueurs
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

        } catch (Exception e) {
            System.out.println("Erreur Inconnu (Réf: " + e + "). Arrêt du Serveur");
        }
    }
}