import awesome_warrior_game.AwesomeWarriorGameClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final int NUMBER_OF_LINES = 2;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] challenges_decisions = in.readLine().split(" ");

        AwesomeWarriorGameClass game = new AwesomeWarriorGameClass(Integer.parseInt(challenges_decisions[0]), Integer.parseInt(challenges_decisions[1]));


        for(int i = 0; i < Integer.parseInt(challenges_decisions[1]); i++){
            String[] edge = in.readLine().split(" ");

            if(edge[1].equals("Pays")){
                game.addEdge(Integer.parseInt(edge[0]),Integer.parseInt(edge[3]),-Integer.parseInt(edge[2]), i);
            }else{
                game.addEdge(Integer.parseInt(edge[0]),Integer.parseInt(edge[3]),Integer.parseInt(edge[2]), i);
            }
        }

        String[] start_end_energy = in.readLine().split(" ");
        String a = game.solve(Integer.parseInt(start_end_energy[0]),Integer.parseInt(start_end_energy[1]),Integer.parseInt(start_end_energy[2]));
        System.out.println(a);
    }
}
