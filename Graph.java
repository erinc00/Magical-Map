/**
 * The Graph class represents a grid-based graph structure where nodes and edges are managed.
 * It includes methods to add nodes, define travel times between nodes, compute shortest paths,
 * and update visibility based on a radius of line of sight.
 */

import java.util.ArrayList;

class Graph {
    int rows, cols; //Dimensions of the grid
    Node[][] map; // 2D array to represent the grid of nodes
    int radius; // Line of sight radius
    Type[] types=new Type[10000000]; //Array to store types
    Hash<Integer, Hash<Integer, Double>> travelTime; // Stores travel times between nodes


    public Graph(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.map = new Node[rows][cols];
        this.travelTime = new Hash<>();
    }

    /**
     * Adds a node to the graph with its coordinates and type.
     * @param x X-coordinate of the node.
     * @param y Y-coordinate of the node.
     * @param type The type of the node.
     */
    public void addNode(int x, int y, int type) {
        if(types[type]!=null){
            map[x][y] = new Node(x, y, types[type]);
            if(map[x][y].type.t==0||map[x][y].type.t==1){
                map[x][y].discovered=true;
            }
        }
        else{
            Type t=new Type(type);
            map[x][y] = new Node(x, y, t);
            types[type]=t;
            if(map[x][y].type.t==0||map[x][y].type.t==1){
                map[x][y].discovered=true;
            }
        }
    }

    /**
     * Adds a travel time between two nodes.
     * @param x1 X-coordinate of the first node.
     * @param y1 Y-coordinate of the first node.
     * @param x2 X-coordinate of the second node.
     * @param y2 Y-coordinate of the second node.
     * @param time Travel time between the two nodes.
     */
    public void addTravelTime(int x1, int y1, int x2, int y2, double time) {
        int idx1 = x1 * cols + y1;
        int idx2 = x2 * cols + y2;

        // Add travel time from idx1 to idx2
        if (!travelTime.contains(idx1)) {
            travelTime.add(idx1, new Hash<Integer, Double>());
        }
        Hash<Integer, Double> neighbors1 = travelTime.get(idx1).v;
        if (neighbors1.contains(idx2)) {
            neighbors1.remove(idx2);
        }
        neighbors1.add(idx2, time);

        // Add travel time from idx2 to idx1
        if (!travelTime.contains(idx2)) {
            travelTime.add(idx2, new Hash<Integer, Double>());
        }
        Hash<Integer, Double> neighbors2 = travelTime.get(idx2).v;
        if (neighbors2.contains(idx1)) {
            neighbors2.remove(idx1);
        }
        neighbors2.add(idx1, time);
    }

    /**
     * Retrieves the travel time between two nodes.
     * @param currentX X-coordinate of the current node.
     * @param currentY Y-coordinate of the current node.
     * @param newX X-coordinate of the target node.
     * @param newY Y-coordinate of the target node.
     * @return Travel time between the nodes, or Double.POSITIVE_INFINITY if no path exists.
     */
    public double getEdgeTime(int currentX, int currentY, int newX, int newY) {
        int idxCurrent = currentX * cols + currentY;
        int idxNew = newX * cols + newY;
        if (!travelTime.contains(idxCurrent)) {
            return Double.POSITIVE_INFINITY;
        }
        Hash<Integer, Double> neighbors = travelTime.get(idxCurrent).v;
        if (!neighbors.contains(idxNew)) {
            return Double.POSITIVE_INFINITY;
        }

        Hash<Integer, Double>.Element<Integer, Double> edgeElement = neighbors.get(idxNew);
        if (edgeElement == null) {
            return Double.POSITIVE_INFINITY;
        }
        return edgeElement.v;
    }

    /**
     * Updates visibility of nodes within a specified radius from the current position.
     * @param cx X-coordinate of the current position.
     * @param cy Y-coordinate of the current position.
     * @param radius Radius within which nodes are revealed.
     * @param hs Path to check for invalid nodes.
     * @return True if the path is valid; false otherwise.
     */
    public boolean updateVisibility(int cx, int cy, int radius, ArrayList<Node> hs) {
        boolean isPathValid=true;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if(map[x][y].type.t<2){
                    continue;
                }
                double distance =Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy));
                if (distance <= (double) radius) {
                    map[x][y].discovered = true;
                    if(hs.contains(map[x][y])){
                        isPathValid=false;
                    }
                }
            }
        }
        return isPathValid;
    }

    /**
     * Implements Dijkstra's algorithm to find the shortest path between two nodes.
     * @param startX X-coordinate of the start node.
     * @param startY Y-coordinate of the start node.
     * @param endX X-coordinate of the target node.
     * @param endY Y-coordinate of the target node.
     * @param path List to store the nodes in the shortest path.
     * @return The cost of the shortest path to the target node.
     */
    public double dijkstra(int startX, int startY, int endX, int endY, ArrayList<Node> path) {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                map[x][y].cost = Double.MAX_VALUE;
                map[x][y].prev = null;
            }
        }
        MinHeap minHeap = new MinHeap(rows * cols);
        map[startX][startY].cost = 0;
        map[startX][startY].prev = map[startX][startY];
        minHeap.add(map[startX][startY]);
        while (!minHeap.isEmpty()) {
            Node current = minHeap.poll();
            if (current.x == endX && current.y == endY) {
                break;
            }
            for (int[] directions : new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}}) {
                int newX = current.x + directions[0];
                int newY = current.y + directions[1];
                if (isValid(newX, newY)) {
                    Node neighbor = map[newX][newY];
                    if((neighbor.discovered&&neighbor.type.t>=1)){
                        continue;
                    }
                    double newCost = current.cost + getEdgeTime(current.x, current.y, newX, newY);
                    if(newCost < neighbor.cost){
                        neighbor.cost = newCost;
                        neighbor.prev=current;
                        minHeap.add(neighbor);
                    }
                }
            }
        }
        Node current = map[endX][endY];
        while(current!=map[startX][startY]&&current!=null){
            path.addFirst(current);
            current=current.prev;
        }
        return map[endX][endY].cost;
    }

    /**
     * Checks if the given coordinates are valid within the grid boundaries.
     * @param x X-coordinate to validate.
     * @param y Y-coordinate to validate.
     * @return True if the coordinates are within bounds; false otherwise.
     */
    private boolean isValid(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }
}
