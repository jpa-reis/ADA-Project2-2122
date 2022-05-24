package awesome_warrior_game;

import java.util.*;

public class AwesomeWarriorGameClass implements AwesomeWarriorGame {

    private static final String FULL = "Full of energy";
    private static final String EMPTY = "0";

    private int numChallenges;
    private int numDecisions;
    private int[][] edges;

    private double[] length;
    private double[] via;


    public AwesomeWarriorGameClass(int challenges, int decisions){
        edges = new int[decisions][3];
        length = new double[challenges];
        via = new double[challenges];
        numChallenges = challenges;
        numDecisions = decisions;

    }

    @Override
    public void addEdge(int initialVertex, int finalVertex, int value, int position) {
        edges[position][0]=initialVertex;
        edges[position][1]=finalVertex;
        edges[position][2]=value;
    }

    @Override
    public String solve(int startingPoint, int endPoint, int totalEnergy) {
        for(int i = 0; i < numChallenges; i++){
            length[i] = Double.NEGATIVE_INFINITY;
            via[i] = -1;
        }
        length[startingPoint] = 0;
        via[startingPoint] = startingPoint;

        boolean changes = false;
        for(int i = 1; i<numChallenges; i++){
            changes = updateLengths();

            if(!changes){
                break;
            }
        }

        //Testing if there is a POSITIVE CYCLE - negative cycles are ignored
        if (changes && updateLengths()){
            System.out.println("CYCLE");
        }

        if(length[endPoint]>=0){
            return FULL;
        }
        else if(length[endPoint] + totalEnergy < 0){
            return EMPTY;
        }
        return Integer.toString((int) (length[endPoint] + totalEnergy));

    }

    //Os valores só mudam quando há ciclo
    private boolean updateLengths() {
        boolean changes = false;
        for(int i = 0; i < numDecisions; i++){
            if(length[edges[i][0]] > Double.NEGATIVE_INFINITY){
                double newLen = length[edges[i][0]] + edges[i][2];
                if(newLen>length[edges[i][1]]){
                    length[edges[i][1]] = newLen;
                    via[edges[i][1]] = edges[i][0];
                    changes = true;
                }
            }
        }

        return changes;
    }
}
/*
*
*5 6
0 Gets 100 3
0 Pays 100 1
1 Gets 1 2
2 Gets 1 1
2 Gets 1 4
3 Pays 150 4
0 4 100
*
* */