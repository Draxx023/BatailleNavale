package BatailleNavale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.*;

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
                    "\nPositionner le premier bateau de ta flotte de longueur 3 (exemple: A1 V pour A1-A2-A3 ou A1 H pour A1-B1-C1).");
            Pattern pattern = Pattern.compile("^[A-E][1-5] [HV]$");
            Matcher matcher;
            String choix;
            int colB1, rowB1;
            char directionB1;
            System.out.print("Ton choix: ");
            choix = sc.nextLine();
            matcher = pattern.matcher(choix);
            while (!matcher.matches()) {
                System.out.print(
                        "Mauvaise saisie\nTon choix: ");
                choix = sc.nextLine();
                matcher = pattern.matcher(choix);
            }
            boolean badInput;
            do {
                badInput = false;
                colB1 = choix.charAt(0) - 65;
                rowB1 = choix.charAt(1) - 49;
                directionB1 = choix.charAt(3);
                if (directionB1 == 'H' && colB1 + 3 > 5) {
                    badInput = true;
                    System.out.println("Ton bateau sera dehors la zone du jeu!");
                    System.out.print(
                            "Choisir une nouvelle position: ");
                    choix = sc.nextLine();
                    matcher = pattern.matcher(choix);
                    while (!matcher.matches()) {
                        System.out.print(
                                "Mauvaise saisie!\nChoisir une nouvelle position: ");
                        choix = sc.nextLine();
                        matcher = pattern.matcher(choix);
                    }
                }
                if (directionB1 == 'V' && rowB1 + 3 > 5) {
                    badInput = true;
                    System.out.println("Ton bateau sera dehors la zone du jeu!");
                    System.out.print(
                            "Choisir une nouvelle position: ");
                    choix = sc.nextLine();
                    matcher = pattern.matcher(choix);
                    while (!matcher.matches()) {
                        System.out.print(
                                "Mauvaise saisie!\nChoisir une nouvelle position: ");
                        choix = sc.nextLine();
                        matcher = pattern.matcher(choix);
                    }
                }
            } while (badInput);
            if (directionB1 == 'V') {
                for (int i = 0; i < 3; i++) {
                    monPlateau[colB1][rowB1 + i] = 'o';
                }
            } else if (directionB1 == 'H') {
                for (int i = 0; i < 3; i++) {
                    monPlateau[colB1 + i][rowB1] = 'o';
                }
            }

            // Deuxième bateau
            int colB2, rowB2;
            char directionB2;
            System.out.println("\nPositionner le deuxième navire de ta flotte de longueur 2");
            System.out.print("Ton choix: ");
            choix = sc.nextLine();
            matcher = pattern.matcher(choix);
            while (!matcher.matches()) {
                System.out.print(
                        "Mauvaise saisie\nTon choix: ");
                choix = sc.nextLine();
                matcher = pattern.matcher(choix);
            }
            do {
                badInput = false;
                colB2 = choix.charAt(0) - 65;
                rowB2 = choix.charAt(1) - 49;
                directionB2 = choix.charAt(3);
                if (directionB2 == 'H' && colB2 + 2 > 5) {
                    badInput = true;
                    System.out.println("Ton bateau sera dehors la zone du jeu!");
                    System.out.print(
                            "Choisir une nouvelle position: ");
                    choix = sc.nextLine();
                    matcher = pattern.matcher(choix);
                    while (!matcher.matches()) {
                        System.out.print(
                                "Mauvais saisie\nChoisir une nouvelle position: ");
                        choix = sc.nextLine();
                        matcher = pattern.matcher(choix);
                    }
                }
                if (directionB2 == 'V' && rowB2 + 2 > 5) {
                    badInput = true;
                    System.out.println("Ton bateau sera dehors la zone du jeu!");
                    System.out.print(
                            "Choisir une nouvelle position: ");
                    choix = sc.nextLine();
                    matcher = pattern.matcher(choix);
                    while (!matcher.matches()) {
                        System.out.print(
                                "Mauvaise saisie\nChoisir une nouvelle position: ");
                        choix = sc.nextLine();
                        matcher = pattern.matcher(choix);
                    }
                }
            } while (badInput);
            if (directionB2 == 'V') {
                for (int i = 0; i < 2; i++) {
                    monPlateau[colB2][rowB2 + i] = 'o';
                }
            } else if (directionB2 == 'H') {
                for (int i = 0; i < 2; i++) {
                    monPlateau[colB2 + i][rowB2] = 'o';
                }
            }
            String payload = "";
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    payload += monPlateau[i][j];
                }
                if (i != 4)
                    payload += " ";
            }
            out.println(payload);
            System.out.println("En attendant le deuxième joueur....");
            int lastMoveRow, lastMoveCol, col, row;
            String debut = in.readLine();
            if (debut.equals("Debut!")) {
                System.out
                        .println("\n\t\t\tDébut du match! (Vous pouvez quitter à tout moment en saisissant 'quit')\n");
                while (command != "quit") {
                    // output
                    afficherTableaux(monPlateau, plateauAdv);
                    System.out.print("C'est ton tour! Coordonnée: > ");
                    command = sc.nextLine();
                    pattern = Pattern.compile("^[A-E][1-5]$");
                    matcher = pattern.matcher(command);
                    while (!matcher.matches()) {
                        System.out.print("Rappel: les coordonnées sont sous le format A1 jusqu'à E5\nRéessayer svp: ");
                        command = sc.nextLine();
                        matcher = pattern.matcher(command);
                    }
                    out.println(command);
                    lastMoveCol = command.charAt(0) - 65;
                    lastMoveRow = command.charAt(1) - 49;
                    // input
                    do {
                        coord = in.readLine(); // coord parse a1 -> 1 ; 1
                    } while (coord == null);
                    col = coord.charAt(0) - 65; // letter
                    row = coord.charAt(1) - 49; // number
                    System.out.println("INFO: Ton adversaire a bombardé " + coord + "!");
                    if (monPlateau[col][row] == 'o') {
                        monPlateau[col][row] = 'ø';
                    } else if (monPlateau[col][row] == '-') {
                        monPlateau[col][row] = 'x';
                    }
                    do {
                        hitOrMiss = in.readLine();
                    } while (hitOrMiss == null);
                    if (hitOrMiss.equals("h")) {
                        System.out.println("Touché!");
                        plateauAdv[lastMoveCol][lastMoveRow] = 'ø';
                    } else if (hitOrMiss.equals("m")) {
                        System.out.println("Raté!");
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
        } catch (

        Exception e) {
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