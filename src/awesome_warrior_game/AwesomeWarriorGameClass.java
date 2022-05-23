package awesome_warrior_game;

import java.util.*;

public class AwesomeWarriorGameClass implements AwesomeWarriorGame {

    private List[] edges;
    private List[] weights;

    public AwesomeWarriorGameClass(int challenges){
        edges = new LinkedList[challenges];
        weights = new LinkedList[challenges];
        for(int i = 0; i<challenges; i++){
            edges[i] = new LinkedList();
            weights[i] = new LinkedList();
        }

    }

    @Override
    public void addEdge(int initialVertex, int finalVertex, int value) {
        edges[initialVertex].add(finalVertex);
        weights[initialVertex].add(value);
    }

    @Override
    public String solve(int startingPoint, int endPoint, int totalEnergy) {
        return null;
    }
}
