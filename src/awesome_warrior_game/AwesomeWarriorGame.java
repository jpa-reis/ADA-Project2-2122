/**
 * Interface for the game
 * @author Joao Tiago Duarte Dos Santos 57957
 * @author Joao Pedro Araujo dos Reis 58175
 */

package awesome_warrior_game;

public interface AwesomeWarriorGame {
    /**
     * Adds an edge to the graph
     * @param initialNode initial node of the edge
     * @param finalNode final node of the edge
     * @param value weight of the edge
     * @param position position in which the edge was added
     */
    void addEdge(int initialNode, int finalNode, int value, int position);


    /**
     * Solves the problem
     * @param startingPoint point where the warrior's adventure starts (initial node of the graph)
     * @param endPoint point where the warrior's adventure ends (final node of the graph)
     * @param totalEnergy total energy of the warrior
     * @return maximum amount of energy the warrior can have at the end (max length of final node)
     */
    int solve(int startingPoint, int endPoint, int totalEnergy);
}
