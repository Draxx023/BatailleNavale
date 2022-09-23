package BatailleNavale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Joueur {
    /*
     * Il faudra mettre en place un protocol adapté
     * La squelette du chat peut-être utilisé mais attention aux détails
     * 
     * @TODO:
     * MEMORISER leur connaissance de la partie
     * récréer la map à chaque main jouée
     * 
     * @PLUS: chatter avec les autres joueurs
     * 
     * @IDEAS:
     * mainClient class:
     * créer un socket
     * PrintWriter instance
     * Listening thread pour les réponses serveur en parallèle
     * 
     * Accèder au serveur
     * Choisir son adversaire
     * Choisir sa formation
     * Commencer le jeu quand l'autre est prêt aussi
     * choisir à chaque fois les coordonnées du point à attaquer
     * iterer jusqu'à ce que tous la flotte est détruite
     * choix de rester en salle d'attente ou sortir du serveur
     */
    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 1234);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.println("Connexion réussie!");
            Scanner sc = new Scanner(System.in);
            String message = "";
            while (message != "quit") {
                message = in.readLine();
                System.out.println(message);
                // @TODO: multiple inputs error
                if (message == "En attendant le joeur n°2....")
                    continue;
                message = sc.nextLine();
                out.println(message);
            }
            sc.close();
            s.close();
        } catch (Exception e) {
            // Traitement d'erreur
        }
    }

    // public static String read(BufferedReader input) {

    // try {
    // // check start of exchange
    // String first = input.readLine();
    // if (first != "\nstart") {
    // System.out.println(first);
    // System.out.println("Input error. Abort communication");
    // return null;
    // }
    // String msg = input.readLine();
    // // check end of exchange
    // if (input.readLine() != "end") {
    // System.out.println("Input error. Abort communication");
    // return null;
    // }
    // return msg;
    // } catch (Exception e) {
    // System.out.println(e);
    // return null;
    // }
    // }
}