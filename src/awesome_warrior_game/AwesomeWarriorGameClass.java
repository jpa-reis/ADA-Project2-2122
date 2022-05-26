/**
 * Implementation of the Interface AwesomeWarriorGame
 * @author Joao Tiago Duarte Dos Santos 57957
 * @author Joao Pedro Araujo dos Reis 58175
 */

package awesome_warrior_game;
import java.util.Stack;

public class AwesomeWarriorGameClass implements AwesomeWarriorGame {


    private final int numChallenges;
    private final int numDecisions;

    /**
     * Variable that saves all the edges of the graph
     * edge[x][0]: initial node of edge x
     * edge[x][1]: final node of edge x
     * edge[x][2]: weight of edge x
     */
    private final int[][] edges;

    /**
     * Contains the length of the path with maximum value to each node
      */
    private final double[] length;

    /**
     * Via is implemented as a graph, the position of each element is a node, the element it contains is its predecessor.
     * This represents a path so there will be as many nodes as there are challenges.
     * We initialize the array with -1 to represent nodes without incoming edges.
     */
    private final Integer[] via;


    /**
     * @param challenges number of challenges (vertex)
     * @param decisions number of decisions (edges)
     */
    public AwesomeWarriorGameClass(int challenges, int decisions) {
        edges = new int[decisions][3];
        length = new double[challenges];
        via = new Integer[challenges];
        for (int i = 0; i < challenges; i++) {
            via[i] = -1;
        }

        numChallenges = challenges;
        numDecisions = decisions;
    }

    @Override
    public void addEdge(int initialNode, int finalNode, int value, int position) {
        edges[position][0] = initialNode;
        edges[position][1] = finalNode;
        edges[position][2] = value;
    }

    @Override
    public int solve(int startingPoint, int endPoint, int totalEnergy) {

        //Initialize variables
        for (int i = 0; i < numChallenges; i++) {
            length[i] = Double.NEGATIVE_INFINITY;
        }
        length[startingPoint] = 0;

        boolean changes = false;
        for (int i = 1; i < numChallenges; i++) {
            changes = updateLengths();

            if (!changes) {
                break;
            }
        }

        if (changes && updateLengths()) {
            if (checkPositiveCycle(endPoint)) {
                return totalEnergy;
            }
        }

        return (int) length[endPoint] + totalEnergy;

    }

    /**
     * This method uses the same logic as the Bellman-Ford algorithm presented in class
     * @return boolean representing if the execution changed lenght array
     */
    private boolean updateLengths() {
        boolean changes = false;
        for (int i = 0; i < numDecisions; i++) {
            if (length[edges[i][0]] > Double.NEGATIVE_INFINITY) {
                double newLen = length[edges[i][0]] + edges[i][2];
                if (newLen > length[edges[i][1]]) {
                    length[edges[i][1]] = newLen;
                    via[edges[i][0]] = edges[i][1];
                    changes = true;
                }
            }
        }

        return changes;
    }

    /**
     * This method is called when a positive cycle is detected in the graph. It checks if there's a path from the cycle
     * to the final node
     * @param endPoint final node of the graph
     * @return true if there's a path from the cycle to the final node
     */
    private boolean checkPositiveCycle(int endPoint) {
        //isolates the cycle of the via
        isolateCycleOfVia(inDegreeOfEveryNode());

        //add to nodesToCheck the nodes who are part of the cycle
        //make nodes who are part of the cycle "true" in array hasNodeBeenPlacedInStack
        Stack<Integer> nodesToCheck = new Stack<>();
        boolean[] hasNodeBeenPlacedInStack = new boolean[numChallenges];
        for(int i = 0; i < via.length; i++){
            if(via[i] >= 0){
                nodesToCheck.push(i);
                hasNodeBeenPlacedInStack[i]=true;
            }
        }

        //while this is executed, all the nodes who have a path from the cycle to themselfs are added to nodesToCheck
        //if any of those nodes is the final node, then there's a path from the cycle to the final node and this returns true
        //hasNodeBeenPlacedInStack ensures no node is checked twice
        while(!nodesToCheck.isEmpty()){
            int node = nodesToCheck.pop();
            if(node == endPoint){
                return true;
            }
            for(int i = 0; i < numDecisions; i++){
                if(edges[i][0] == node && !hasNodeBeenPlacedInStack[edges[i][1]]){
                    nodesToCheck.push(edges[i][1]);
                    hasNodeBeenPlacedInStack[edges[i][1]] = true;
                }
            }
        }

        return false;
    }

    /**
     * Calculates the degree in every node of the graph "via"
     * @return an array with the in degree of every node
     */
    private int[] inDegreeOfEveryNode() {
        int[] inDegree = new int[via.length];
        for (Integer integer : via) {
            if (integer != -1) inDegree[integer]++;
        }
        return inDegree;
    }

    /**
     * This method isolates the cycle in via if there is one.
     * We use the same logic as topological sorting to detect cycles presented in class.
     * If a node has 0 edges incoming then we eliminate the node (set it to -1) as it doesn't belong to the cycle.
     * We proceed to change the inDegree of the node with an edge from the eleminated node. 
     * As long as the graph changes the cycle continues (this is done a majority of numberOfChallenges times).
     * @param inDegree the inDegree of the nodes provided
     */
    private void isolateCycleOfVia(int[] inDegree) {
        boolean changes = true;

        while (changes) {
            changes = false;
            for (int i = 0; i < via.length; i++) {
                if (inDegree[i] == 0 && via[i] != -1) {
                    inDegree[via[i]]--;
                    via[i] = -1;
                    changes = true;
                }
            }
        }
    }

}