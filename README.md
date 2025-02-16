# Magical Map 🏟️✨

## **Project Overview**

Magical Map is a **dynamic route planning system** that calculates the shortest path between locations in a grid-based magical world. The system utilizes **Dijkstra’s Algorithm** to determine optimal routes while dynamically updating visibility based on the player’s movement.

Unlike traditional shortest path algorithms, **this project introduces an adaptive exploration mechanism** where obstacles and paths are **revealed progressively** as the player moves. The system also includes **decision-making logic**, where specific obstacles can be removed based on an optimal choice strategy.

This simulation models real-world applications in **navigation systems, robotics pathfinding, and AI-driven game mechanics**.

---

## **Features**

👉 **Graph-Based Navigation**: The map is structured as a weighted graph, where nodes represent locations and edges define travel times.\
👉 **Dijkstra’s Algorithm**: Computes the shortest path dynamically as new obstacles are discovered.\
👉 **Dynamic Visibility Updates**: The map starts as partially hidden, revealing new paths only when they come into range.\
👉 **Obstacle Handling & Decision Making**: The system allows removing specific obstacles based on optimal path calculations.\
👉 **Efficient Pathfinding Optimization**: Implements **Min-Heap (Priority Queue)** to optimize Dijkstra’s performance.\
👉 **Custom Hash Table Implementation**: Uses a **double hashing** mechanism for fast access to node and path data.

---

## **Technologies & Algorithms**

🖥 **Programming Language:** Java\
🎨 **Graph Algorithms:** Dijkstra’s Algorithm\
📈 **Data Structures:** Graph, Min-Heap (Priority Queue), Hash Table (Double Hashing)\
🔦 **Visualization:** Outputs movement steps dynamically

---

## **File Structure**

📂 **Main.java** → Reads input files, initializes the graph, and executes shortest pathfinding.\
📂 **Graph.java** → Represents the grid-based map, managing nodes, edges, and travel times.\
📂 **Node.java** → Defines graph nodes with coordinates, type, and cost information.\
📂 **MinHeap.java** → Implements a **priority queue** for Dijkstra’s Algorithm efficiency.\
📂 **Hash.java** → Custom **double hashing** based hash table implementation.\
📂 **Type.java** → Defines different types of nodes and their respective properties.

---

## **How It Works?**

### **1️⃣ Graph Initialization**

- The map is modeled as a **graph with nodes and weighted edges** representing locations and travel costs.
- Nodes are classified as **walkable areas, blocked obstacles, or hidden barriers** that are revealed later.

### **2️⃣ Shortest Path Calculation**

- **Dijkstra’s Algorithm** is used to compute the optimal route.
- If a previously unknown obstacle is encountered, the path is **dynamically recalculated** in real time.

### **3️⃣ Dynamic Visibility Updates**

- The **map starts with only partial visibility**, limiting the player's knowledge of the full landscape.
- As the player moves, **new areas and obstacles are gradually revealed** using a distance-based discovery system.

### **4️⃣ Obstacle Handling & Decision Making**

- In some cases, **specific obstacles can be removed** to create a more efficient route.
- The player can choose an option that provides the **best possible path**, reducing travel time.

---

## **Installation & Usage**

### **Prerequisites**

📌 **Java 8+** must be installed on your system.

### **Compiling the Project**

```sh
javac *.java  
```

### **Running the Project**

```sh
java Main <land_file> <travel_time_file> <mission_file> <output_file>  
```

Example:

```sh
java Main land.txt travel.txt mission.txt output.txt  
```

---

## **Example Output**

```
Moving to 0-1  
Moving to 1-1  
Path is impassable!  
Moving to 0-1  
Moving to 0-2  
Moving to 1-2  
Objective 1 reached!  
Number 3 is chosen!  
```

---

## **Future Improvements**

🔹 Add GUI visualization for real-time pathfinding.\
🔹 Implement **A**\* Algorithm\*\* for improved efficiency over Dijkstra’s approach.\
🔹 Optimize **hash table performance** for large-scale maps.

---

## **Author**

👤 **Ethem Erinc Cengiz**

📧 [erinccengiz@gmail.com](mailto\:erinccengiz@gmail.com)

🔗 [GitHub](https://github.com/erinc00)
