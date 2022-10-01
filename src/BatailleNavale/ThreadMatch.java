package BatailleNavale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadMatch extends Thread {
    Socket j1, j2;
    int idMatch;
    BufferedReader inputJ1, inputJ2;
    PrintWriter outputJ1, outputJ2;
    char formationJ1[][] = new char[5][5];
    char formationJ2[][] = new char[5][5];

    public ThreadMatch(int idMatch, Socket j1, Socket j2) {
        try {
            this.j1 = j1;
            this.j2 = j2;
            this.idMatch = idMatch;
            inputJ1 = new BufferedReader(new InputStreamReader(j1.getInputStream()));
            inputJ2 = new BufferedReader(new InputStreamReader(j2.getInputStream()));
            outputJ1 = new PrintWriter(j1.getOutputStream(), true);
            outputJ2 = new PrintWriter(j2.getOutputStream(), true);
            System.out.println("Match n°" + idMatch + " has started!");
        } catch (Exception e) {
            System.out.println("Erreur: " + e + ". Abort.");
        }
    }

    public static void afficherTableaux(char[][] mP, char[][] pA, int id) {
        System.out.print("Match n°" + id + "\n  Plateau joueur 1\t\t\tPlateau joueur 2\n");
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

    public void run() {
        try {
            // get the two boards
            String boardJ1 = inputJ1.readLine();
            String boardJ2 = inputJ2.readLine();
            // reconstruction des plateaux
            String[] resJ1 = boardJ1.split(" ");
            String[] resJ2 = boardJ2.split(" ");
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    formationJ1[i][j] = resJ1[i].charAt(j);
                    formationJ2[i][j] = resJ2[i].charAt(j);
                }
            }
            // Début
            outputJ1.println("Debut!");
            outputJ2.println("Debut!");

            String choixJ1, choixJ2;
            int rowJ1, rowJ2, colJ1, colJ2;

            while (true) {
                do {
                    choixJ1 = inputJ1.readLine();
                } while (choixJ1 == null);
                do {
                    choixJ2 = inputJ2.readLine();
                } while (choixJ2 == null);
                System.out.println("Joueur n°1 a choisi: " + choixJ1);
                System.out.println("Joueur n°2 a choisi: " + choixJ2);

                colJ1 = choixJ1.charAt(0) - 65; // letter
                rowJ1 = choixJ1.charAt(1) - 49; // number
                colJ2 = choixJ2.charAt(0) - 65; // letter
                rowJ2 = choixJ2.charAt(1) - 49; // number

                // 3 readlines : coord, hitOrMiss (hit-h, miss-m), status of game (win-w,
                // lose-l)
                outputJ2.println(choixJ1);
                outputJ1.println(choixJ2);
                if (formationJ2[colJ1][rowJ1] == 'o') {
                    System.out.println("Joueur n°1 a touché un navire du joueur n°2!");
                    outputJ1.println('h');
                    formationJ2[colJ1][rowJ1] = 'ø';
                } else if (formationJ2[colJ1][rowJ1] == '-') {
                    System.out.println("Joueur n°1 a raté le coup!");
                    outputJ1.println('m');
                }
                if (formationJ1[colJ2][rowJ2] == 'o') {
                    System.out.println("Joueur n°2 a touché un navire du joueur n°1!");
                    outputJ2.println('h');
                    formationJ1[colJ2][rowJ2] = 'ø';
                } else if (formationJ1[colJ2][rowJ2] == '-') {
                    System.out.println("Joueur n°2 a raté le coup!");
                    outputJ2.println('m');
                }
                boolean j1Lost, j2Lost;
                j1Lost = true;
                j2Lost = true;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (formationJ1[i][j] == 'o') {
                            j1Lost = false;
                        }
                        if (formationJ2[i][j] == 'o') {
                            j2Lost = false;
                        }
                    }
                }
                if (j2Lost) {
                    outputJ1.println("x");
                    outputJ2.println("l");
                    System.out.println("Match terminé! Joueur n°1 gagne.");
                    break;
                }
                if (j1Lost) {
                    outputJ2.println("w");
                    outputJ1.println("l");
                    System.out.println("Match terminé! Joueur n°2 gagne.");
                    break;
                }
                if (!j1Lost && !j2Lost) {
                    outputJ2.println("");
                    outputJ1.println("");
                    afficherTableaux(formationJ1, formationJ2, this.idMatch);
                    System.out.println("En attendant les joueurs...");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}