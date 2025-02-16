/**
 * MinHeap class is a minimum priority queue implementation based on a binary heap structure.
 * It maintains an array-based heap where each parent node has a lower cost than its children.
 */

class MinHeap {
    private Node[] heap; // Array to store the nodes in the heap
    private int size; // Current size of the heap
    private int capacity; // Maximum capacity of the heap

    /**
     * Constructor to initialize the MinHeap with a given capacity.
     * @param capacity The maximum number of nodes the heap can hold.
     */
    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new Node[capacity];
    }

    /**
     * Adds a node to the MinHeap and ensures the heap property is maintained.
     * @param node The node to be added to the heap.
     */
    public void add(Node node) {
        if (size == capacity) return; // Heap is full, no addition possible
        heap[size] = node; // Add the node at the end of the heap
        int current = size;

        // Bubble up the node to maintain the heap property
        while (current > 0 && heap[current].cost < heap[parent(current)].cost) {
            swap(current, parent(current));
            current = parent(current);
        }
        size++;
    }

    /**
     * Removes and returns the node with the minimum cost (root node) from the heap.
     * @return The root node with the minimum cost, or null if the heap is empty.
     */
    public Node poll() {
        if (size == 0) return null; // Return null if the heap is empty
        Node root = heap[0]; // Store the root node to return
        heap[0] = heap[size - 1]; // Move the last node to the root
        size--;
        heapify(0); // Restore the heap property by heapifying downward
        return root;
    }

    /**
     * Heapify method to restore the heap property starting from the given index.
     * @param i The index at which to start heapifying.
     */
    private void heapify(int i) {
        int left = leftChild(i);
        int right = rightChild(i);
        int smallest = i;

        // Find the smallest node among the current node and its children
        if (left < size && heap[left].cost < heap[smallest].cost) {
            smallest = left;
        }
        if (right < size && heap[right].cost < heap[smallest].cost) {
            smallest = right;
        }

        // If the smallest node is not the current node, swap and continue heapifying
        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }

    /**
     * Helper method to find the parent index of a given index.
     * @param i The index of the current node.
     * @return The index of the parent node.
     */
    private int parent(int i) { return (i - 1) / 2; }

    /**
     * Helper method to find the left child index of a given index.
     * @param i The index of the current node.
     * @return The index of the left child node.
     */
    private int leftChild(int i) { return 2 * i + 1; }

    /**
     * Helper method to find the right child index of a given index.
     * @param i The index of the current node.
     * @return The index of the right child node.
     */
    private int rightChild(int i) { return 2 * i + 2; }

    /**
     * Swaps two nodes in the heap.
     * @param i The index of the first node.
     * @param j The index of the second node.
     */
    private void swap(int i, int j) {
        Node temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Checks if the heap is empty.
     * @return True if the heap is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }
}

