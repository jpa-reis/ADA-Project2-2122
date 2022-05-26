package awesome_warrior_game;

import java.net.Inet4Address;
import java.util.*;

public class AwesomeWarriorGameClass implements AwesomeWarriorGame {

    private static final String FULL = "Full of energy";
    private static final String EMPTY = "0";

    private final int numChallenges;
    private final int numDecisions;
    private final int[][] edges;

    private final double[] length;
    private final ArrayList<Integer> via;

    public AwesomeWarriorGameClass(int challenges, int decisions){
        edges = new int[decisions][3];
        length = new double[challenges];
        via = new ArrayList<>(challenges);
        for(int i = 0; i < challenges; i++){
            via.add(i, -1);
        }
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

        //Initialize variables
        for(int i = 0; i < numChallenges; i++){
            length[i] = Double.NEGATIVE_INFINITY;
        }
        length[startingPoint] = 0;

        boolean changes = false;
        for(int i = 1; i<numChallenges; i++){
            changes = updateLengths();

            if(!changes){
                break;
            }
        }


        if (changes && updateLengths()){
            if(getCycleNodes(endPoint)){
                return FULL;
            }
        }

        if(length[endPoint]>=0){
            return FULL;
        }
        else if(length[endPoint] + totalEnergy < 0){
            return EMPTY;
        }
        return Integer.toString((int) (length[endPoint] + totalEnergy));

    }

    private boolean updateLengths() {
        boolean changes = false;
        for(int i = 0; i < numDecisions; i++){
            if(length[edges[i][0]] > Double.NEGATIVE_INFINITY){
                double newLen = length[edges[i][0]] + edges[i][2];
                if(newLen>length[edges[i][1]]){
                    length[edges[i][1]] = newLen;
                    via.set(edges[i][0], edges[i][1]);
                    changes = true;
                }
            }
        }

        return changes;
    }

    private boolean getCycleNodes(int endPoint){
        int[] inDegree = new int[numChallenges];
        boolean changes =true;

        for (Integer integer : via) {
            if (integer != -1) inDegree[integer]++;
        }

        //algorithm that isolates the cycle
        while(changes){
            changes = false;
            for(int i = 0; i < numChallenges; i++){
                if(inDegree[i] == 0 && via.get(i) != -1){
                    int u = via.set(i, -1);
                    inDegree[u]--;
                    changes = true;
                }
            }

        }

        //list with node identified as a cycle
        //preferably do this in the previous cycle and eliminate this for loop
        List<Integer> aux = new ArrayList<>();
        for(int i = 0; i < via.size(); i++){
            if(via.get(i) > 0){
                aux.add(i);
            }
        }

        //procura pelo grafo de x nós até chegar a um endpoint
        //create a seperate reachesNode() function
        for(int i = 0; i < numDecisions; i++){
            for(int u = 0; u< aux.size(); u++){
                if(edges[i][0] == aux.get(u) && !aux.contains(edges[i][1])){
                    aux.add(edges[i][1]);
                }
                if(aux.get(u) == endPoint){
                    return true;
                }
            }
        }

        return false;
    }
}