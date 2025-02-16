class Node{
    int x, y;     // The x and y coordinates of the node in the grid.
    Type type;    // The type of the node.
    double cost;    // The cost associated with the node, typically used in pathfinding algorithms.
    boolean discovered;    // Indicates whether the node has been discovered by the algorithm.
    Node prev;    // The previous node in the path, used to trace the path after reaching the destination.

    public Node(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.cost = Double.MAX_VALUE;
        this.discovered = false;
    }

    public double compareTo(Node other) {
        return cost - other.cost;
    }

}
