package BatailleNavale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadMatch extends Thread {
    Socket j1, j2;
    BufferedReader inputJ1, inputJ2;
    PrintWriter outputJ1, outputJ2;
    String formationJ1[] = new String[2];
    String formationJ2[] = new String[2];

    public ThreadMatch(int idMatch, Socket j1, Socket j2) {
        try {
            this.j1 = j1;
            this.j2 = j2;
            inputJ1 = new BufferedReader(new InputStreamReader(j1.getInputStream()));
            inputJ2 = new BufferedReader(new InputStreamReader(j2.getInputStream()));
            outputJ1 = new PrintWriter(j1.getOutputStream(), true);
            outputJ2 = new PrintWriter(j2.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println("Erreur: " + e + ". Abort.");
        }
    }

    public void run() {
        try {
            /*
             * Once a match starts I want a function that handles each user's formation
             * something like :
             * byte[] BoardJ1 = formationJoueur(J1);
             * byte[] BoardJ2 = formationJoueur(J2);
             * Then a function that controls the game: each time it gives a user to input a
             * coordinate to hit
             * and updates the states of the boards as well as the log message
             * something like:
             * while(matchEnded){
             * moveJ1 = getMoveJ1(); // takes input of J1
             * updateGame(J2); // updates J2 board
             * moveJ2 = getMoveJ2();
             * updateGame(J1);
             * }
             * Once the match has ended, the winner is announced and the thread is closed
             */
            outputJ1.println("Jeu commencé! Choisi la coordonnée de ton premier navire de taille 5 (ex: A1 v):");
            String input = inputJ1.readLine();
            String[] data = input.split(" ");
            String cord = data[0];
            String direction = data[1];
            if(direction.equals("v")){
                
            }
            formationJ1[0] = inputJ1.readLine();
            System.out.println("Joueur 1 - Cordonnée 1: " + formationJ1[0]);
            outputJ1.println("Coordonnée finale:");
            formationJ1[1] = inputJ1.readLine();
            System.out.println("Joueur 1 - Cordonnée 2: " + formationJ1[1]);
            outputJ2.println("C'est maintenant ton tour! Choisi la première coordonnée de ton navire:");
            formationJ2[0] = inputJ2.readLine();
            System.out.println("Joueur 2 - Cordonnée 1: " + formationJ2[0]);
            outputJ2.println("Coordonnée finale:");
            formationJ2[1] = inputJ2.readLine();
            System.out.println("Joueur 2 - Cordonnée 2: " + formationJ2[1]);
            String choixJ1, choixJ2;
            int compteurJ1 = 2;
            int compteurJ2 = 2;
            outputJ1.println("Début Match! Coordonnée à bombarder:");
            outputJ2.println("Début Match! Coordonnée à bombarder:");
            while (true) {
                choixJ1 = inputJ1.readLine();
                System.out.println("Joueur n°1 a choisi: " + choixJ1);
                choixJ2 = inputJ2.readLine();
                System.out.println("Joueur n°2 a choisi: " + choixJ2);
                if (formationJ1[0].equals(choixJ2) || formationJ1[1].equals(choixJ2)) {
                    compteurJ1--;
                    System.out.println("Navire J1 Touché - Rem: " + compteurJ1);
                    if (formationJ1[0] == choixJ2)
                        formationJ1[0] = "";
                    if (formationJ1[1] == choixJ2)
                        formationJ1[1] = "";
                    if (compteurJ1 == 0 || compteurJ2 == 0) {
                        if (compteurJ1 == 0)
                            outputJ1.println("Perdu!");
                        else
                            outputJ2.println("Gagné!");
                        System.out.println("Match Fini, J1 Perdu");
                        break;
                    } else {
                        outputJ1.println("L'adversaire a bombardé " + choixJ2
                                + "! Ton navire a été touché! Coordonnée à bombarder:");
                    }
                } else {
                    outputJ1.println("L'adversaire a bombardé " + choixJ2
                            + "! Ton navire n'a pas été touché! Coordonnée à bombarder:");
                }
                if (formationJ2[0].equals(choixJ1) || formationJ2[1].equals(choixJ1)) {
                    compteurJ2--;
                    System.out.println("Navire J2 Touché - Rem: " + compteurJ2);
                    if (formationJ2[0] == choixJ1)
                        formationJ2[0] = "";
                    if (formationJ2[1] == choixJ1)
                        formationJ2[1] = "";
                    if (compteurJ1 == 0 || compteurJ2 == 0) {
                        if (compteurJ2 == 0) {
                            outputJ1.println("Perdu!");
                            j1.close();
                            j2.close();
                        } else {
                            outputJ2.println("Gagné!");
                            j1.close();
                            j2.close();
                        }
                        System.out.println("Match Fini, J2 Perdu");
                        break;
                    } else {
                        outputJ2.println("L'adversaire a bombardé " + choixJ1
                                + "! Ton navire a été touché! Coordonnée à bombarder:");
                    }
                } else {
                    outputJ2.println("L'adversaire a bombardé " + choixJ1
                            + "! Ton navire n'a pas été touché! Coordonnée à bombarder:");
                }
            }
        } catch (Exception e) {

        }
    }

}