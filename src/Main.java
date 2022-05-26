/**
 * Main class of the program
 * @author Joao Tiago Duarte Dos Santos 57957
 * @author Joao Pedro Araujo dos Reis 58175
 */

import awesome_warrior_game.AwesomeWarriorGame;
import awesome_warrior_game.AwesomeWarriorGameClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final String FULL = "Full of energy";
    private static final String EMPTY = "0";
    private static final String PAYS = "Pays";


    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] challenges_decisions = in.readLine().split(" ");
        int challenges = Integer.parseInt(challenges_decisions[0]);
        int decisions = Integer.parseInt(challenges_decisions[1]);

        AwesomeWarriorGame game = new AwesomeWarriorGameClass(challenges, decisions);


        for(int i = 0; i < decisions; i++){
            String[] edge = in.readLine().split(" ");
            int initialVertex = Integer.parseInt(edge[0]);
            int finalVertex = Integer.parseInt(edge[3]);
            int cost = Integer.parseInt(edge[2]);
            String paysOrGets = edge[1];

            if(paysOrGets.equals(PAYS)){
                game.addEdge(initialVertex,finalVertex,-cost, i);
            }else{
                game.addEdge(initialVertex,finalVertex,cost, i);
            }
        }

        String[] start_end_energy = in.readLine().split(" ");
        int start = Integer.parseInt(start_end_energy[0]);
        int end = Integer.parseInt(start_end_energy[1]);
        int energy = Integer.parseInt(start_end_energy[2]);

        int ans = game.solve(start,end,energy);
        if (ans >= energy) {
            System.out.println(FULL);
        } else if (ans < 0) {
            System.out.println(EMPTY);
        } else {
            System.out.println(ans);
        }
    }
}
