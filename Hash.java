import java.util.ArrayList;

/**
 * A generic Hash table implementation that supports storing key-value pairs,
 * rehashing, and converting to MinHeap or MaxHeap structures.
 *
 * @param <Key>   The type of keys used for identifying elements in the hash table.
 * @param <Value> The type of values associated with the keys in the hash table.
 */
public class Hash<Key,Value> {
    public int capacity;
    public int size;
    public Element<Key,Value>[] array;
    public ArrayList<Key>keys;

    /**
     * A nested class representing an element in the hash table.
     *
     * @param <Key>   The type of the key.
     * @param <Value> The type of the value.
     */
    public class Element<Key,Value>{
        Key k;
        Value v;
        int condition;

        /**
         * Constructor for creating an element in the hash table.
         *
         * @param k The key of the element.
         * @param v The value of the element.
         */
        public Element(Key k,Value v){
            this.k=k;
            this.v=v;
            condition=1;
        }
    }

    /**
     * Constructor for the Hash class.
     * Initializes the hash table with a default capacity of 37.
     */
    public Hash(){
        this.array=new Element[37];
        this.capacity=37;
        this.size=0;
        this.keys=new ArrayList<>();
    }

    /**
     * Computes the primary hash value for a given key.
     *
     * @param k The key to hash.
     * @return The hash value.
     */
    public int hash0(Key k){
        return Math.abs(k.hashCode())%capacity;
    }

    /**
     * Computes the secondary hash value for a given key, used for double hashing.
     *
     * @param k The key to hash.
     * @return The secondary hash value.
     */
    public int hash1(Key k){
        return 11-(Math.abs(k.hashCode())%11);
    }

    /**
     * Finds the next prime number greater than or equal to a given number.
     *
     * @param number The number to start checking from.
     * @return The next prime number.
     */
    private int getNextPrime(int number) {
        while (true) {
            if (isPrime(number)) {
                return number;
            }
            number++;
        }
    }

    /**
     * Checks if a given number is prime.
     *
     * @param number The number to check.
     * @return True if the number is prime, false otherwise.
     */
    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves an element from the hash table by key.
     *
     * @param k The key of the element.
     * @return The element if found, otherwise null.
     */
    public Element<Key, Value> get(Key k){
        for(int i=0;i<capacity;i++){
            int index=(hash0(k)+i*hash1(k))%capacity;
            if(array[index]==null){
                return null;
            }
            if((array[index].k.equals(k))&&(array[index].condition==1)){
                return array[index];
            }
            else if((array[index].k.equals(k))&&(array[index].condition==-1)){
                return null;
            }
        }
        return null;
    }

    /**
     * Checks if the hash table contains an element with a given key.
     *
     * @param k The key to check.
     * @return True if the key exists in the hash table, false otherwise.
     */
    public boolean contains(Key k){
        if(get(k)==null){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Adds a key-value pair to the hash table.
     * If the load factor exceeds 0.5, the table is rehashed.
     *
     * @param k The key of the element to add.
     * @param v The value of the element to add.
     */
    public void add(Key k,Value v){
        if((float)size/capacity>=0.5){
            rehash();
        }
        int index=0;
        for(int i=0;i<capacity;i++){
            index=(hash0(k)+i*hash1(k))%capacity;
            if(array[index]==null||array[index].condition==-1){
                array[index]=new Element<>(k,v);
                array[index].condition=1;
                size++;
                return;
            }
        }
    }

    /**
     * Removes an element from the hash table by marking its condition as deleted.
     *
     * @param k The key of the element to remove.
     */
    public void remove(Key k){
        int index=0;
        for(int i=0;i<capacity;i++){
            index=(hash0(k)+i*hash1(k))%capacity;
            if(array[index]!=null&&array[index].k.equals(k)&&array[index].condition==1){
                array[index].condition=-1;
                size--;
                return;
            }
        }
    }

    /**
     * Rehashes the hash table to increase its capacity and redistribute the elements.
     */
    public void rehash(){
        Element<Key, Value>[] saved_array=array;
        array=new Element[getNextPrime(2*capacity)];
        for(int i=0;i< saved_array.length;i++){
            if(saved_array[i]!=null&&saved_array[i].condition!=-1){
                add(saved_array[i].k,saved_array[i].v);
            }
        }
        capacity=array.length;
    }
}
