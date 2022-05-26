package awesome_warrior_game;

import java.util.*;

public class AwesomeWarriorGameClass implements AwesomeWarriorGame {

    private static final String FULL = "Full of energy";
    private static final String EMPTY = "0";

    private final int numChallenges;
    private final int numDecisions;

    /*
     * Variable that saves all the edges of the graph
     * edge[x][0]: initial node of edge x
     * edge[x][1]: final node of edge x
     * edge[x][2]: weight of edge x
     */
    private final int[][] edges;

    // Contains the length of the path with maximum value to each node
    private final double[] length;

    // Arrays that are created during the algorithm. Used to find which nodes belong to a cycle
    private final int[] via;


    public AwesomeWarriorGameClass(int challenges, int decisions){
        edges = new int[decisions][3];
        length = new double[challenges];
        via = new int[challenges];
        for(int i = 0; i < challenges; i++){
            via[i]=-1;
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
                    via[edges[i][0]]= edges[i][1];
                    changes = true;
                }
            }
        }

        return changes;
    }




    private boolean getCycleNodes(int endPoint){
        int[] inDegree = new int[numChallenges];
        boolean changes =true;
        boolean[] checked = new boolean[numChallenges];

        for (Integer integer : via) {
            if (integer != -1) inDegree[integer]++;
        }

        //algorithm that isolates the cycle
        while(changes){
            changes = false;
            for(int i = 0; i < via.length; i++){
                if(inDegree[i] == 0 && via[i] != -1){
                    int u = via[i];
                    via[i] = -1;
                    inDegree[u]--;
                    changes = true;
                }
            }
        }

        //list with node identified as a cycle
        //preferably do this in the previous cycle and eliminate this for loop
        boolean[] checkedNode = new boolean[numChallenges];
        Stack<Integer> nodesToCheck = new Stack();
        for(int i = 0; i < via.length; i++){
            if(via[i] >= 0){
                checkedNode[i]=true;
                nodesToCheck.push(i);
            }
        }

        //procura pelo grafo de x nós até chegar a um endpoint
        //create a seperate reachesNode() function
        while(!nodesToCheck.empty()){
            int node = nodesToCheck.pop();
            if(node == endPoint){
                return true;
            }
            for(int i = 0; i < numDecisions; i++){
                if(edges[i][0] == node && !checkedNode[edges[i][1]]){
                    nodesToCheck.push(edges[i][1]);
                    checkedNode[edges[i][1]] = true;
                }
            }
        }

        return false;
    }
}