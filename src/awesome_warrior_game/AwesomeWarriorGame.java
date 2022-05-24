package awesome_warrior_game;

public interface AwesomeWarriorGame {
    void addEdge(int initialVertex, int finalVertex, int value, int position);
    String solve(int startingPoint, int endPoint, int totalEnergy);
}
