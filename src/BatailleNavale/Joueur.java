package BatailleNavale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Joueur {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 1234);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.println("Connexion réussie!");
            Scanner sc = new Scanner(System.in);
            String command = "", coord, hitOrMiss, status;
            char[][] monPlateau = new char[5][5];
            char[][] plateauAdv = new char[5][5];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    monPlateau[i][j] = '-';
                    plateauAdv[i][j] = '-';
                }
            }
            System.out.println("Voilà les deux plateaux de jeu! Chacun d'entre vous a une surface de 5x5.");
            afficherTableaux(monPlateau, plateauAdv);
            System.out.println("À vous de positionner vos bateaux!");
            System.out.println(
                    "Positionner le premier bateau de ta flotte (1x3) (exemple: A2 V pour A2-A3-A4 - A1 H pour A1-B1-C1): ");
            String choix = sc.nextLine();
            int colB1 = choix.charAt(0) - 65; // letter @NOTE: no input check done yet
            int rowB1 = choix.charAt(1) - 49; // number
            char directionB1 = choix.charAt(3);
            System.out.println(colB1 + " " + rowB1 + " " + directionB1);
            if (directionB1 == 'V') {
                for (int i = 0; i < 3; i++) {
                    monPlateau[colB1][rowB1 + i] = 'o'; // no user input is controlled yet
                }
            } else if (directionB1 == 'H') {
                for (int i = 0; i < 3; i++) {
                    monPlateau[colB1 + i][rowB1] = 'o'; // no user input is controlled yet
                }
            }
            System.out.println("Positionner le deuxième navire de ta flotte (1x2): ");
            choix = sc.nextLine();
            int colB2 = choix.charAt(0) - 65; // letter
            int rowB2 = choix.charAt(1) - 49; // number
            char directionB2 = choix.charAt(3);
            System.out.println(colB2 + " " + rowB2 + " " + directionB2);
            if (directionB2 == 'V') {
                for (int i = 0; i < 2; i++) {
                    monPlateau[colB2][rowB2 + i] = 'o'; // no user input is controlled yet
                }
            } else if (directionB2 == 'H') {
                for (int i = 0; i < 2; i++) {
                    monPlateau[colB2 + i][rowB2] = 'o'; // no user input is controlled yet
                }
            }
            afficherTableaux(monPlateau, plateauAdv);
            String payload = "";
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    payload += monPlateau[i][j];
                }
                if (i != 4)
                    payload += " ";
            }
            out.println(payload);
            int lastMoveRow, lastMoveCol, col, row;
            String debut = in.readLine();
            if (debut.equals("Debut!")) {
                System.out.println("\n\t\t\tDébut du match!\n");
                while (command != "quit") {
                    // output
                    afficherTableaux(monPlateau, plateauAdv);
                    System.out.print("C'est ton tour! Coordonnée: ");
                    command = sc.nextLine();
                    out.println(command);
                    lastMoveCol = command.charAt(0) - 65;
                    lastMoveRow = command.charAt(1) - 49;
                    // input
                    do {
                        coord = in.readLine(); // coord parse a1 -> 1 ; 1
                    } while (coord == null);
                    col = coord.charAt(0) - 65; // letter
                    row = coord.charAt(1) - 49; // number
                    System.out.println("Ton adversaire a bombardé " + coord + "!");
                    if (monPlateau[col][row] == 'o') {
                        System.out.println("Ton navire a été touché!");
                        monPlateau[col][row] = 'ø';
                    } else if (monPlateau[col][row] == '-') {
                        System.out.println("Ton adversaire a raté le coup!");
                        monPlateau[col][row] = 'x';
                    }
                    do {
                        hitOrMiss = in.readLine();
                    } while (hitOrMiss == null);
                    if (hitOrMiss.equals("h")) {
                        System.out.println("T'as touché un bateau!");
                        plateauAdv[lastMoveCol][lastMoveRow] = 'ø';
                    } else if (hitOrMiss.equals("m")) {
                        System.out.println("T'as raté le coup!");
                        plateauAdv[lastMoveCol][lastMoveRow] = 'x';
                    }
                    do {
                        status = in.readLine();
                    } while (status == null);
                    if (status.equals("w")) {
                        afficherTableaux(monPlateau, plateauAdv);
                        System.out.println("Gagné!");
                        break;
                    } else if (status.equals("l")) {
                        afficherTableaux(monPlateau, plateauAdv);
                        System.out.println("Perdu!");
                        break;
                    } else {
                        continue;
                    }
                }
            }
            sc.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void afficherTableaux(char[][] mP, char[][] pA) {
        System.out.print("  Ton plateau de jeu\t\t\tPlateau de jeu adversaire\n");
        System.out.print("  A B C D E\t\t\t\t  A B C D E\n");
        int indx;
        for (int i = 0; i < 5; i++) {
            indx = i + 1;
            System.out.print(indx + " ");
            for (int j = 0; j < 5; j++) {
                System.out.print(mP[j][i] + " ");
            }
            System.out.print("\t\t\t\t" + indx + " ");
            for (int j = 0; j < 5; j++) {
                System.out.print(pA[j][i] + " ");
            }
            System.out.println();
        }
    }
}