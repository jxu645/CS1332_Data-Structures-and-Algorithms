import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Jessie Xu
 * @version 1.0
 * @userid jxu645
 * @GTID 903726541
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * Consider how to most efficiently determine if the list contains null data.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Arraylist passed in could not be null.");
        }
        this.backingArray = (T[]) new Comparable[2 * data.size() + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Element at index i in the data array is null.");
            }
            backingArray[i + 1] = data.get(i);
            this.size++;
        }
        for (int i = size / 2; i >= 1; i--) {
            downheap(backingArray, i);
        }
    }

    /**
     * Helper recursive method that build a heap using down heap algorithm.
     * @param backingArray backingArray that is defined in this class
     * @param i non-leaf parent node to look at
     */
    private void downheap(T[] backingArray, int i) {
        int largerIndex = i;
        int left = 2 * i;
        int right = 2 * i + 1;
        if (left <= size && backingArray[left].compareTo(backingArray[largerIndex]) > 0) {
            largerIndex = left;
        }
        if (right <= size && backingArray[right].compareTo(backingArray[largerIndex]) > 0) {
            largerIndex = right;
        }
        if (largerIndex != i) {
            T temp = backingArray[i];
            backingArray[i] = backingArray[largerIndex];
            backingArray[largerIndex] = temp;
            downheap(backingArray, largerIndex);
        }
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to be added cannot be null");
        }
        if (size == backingArray.length - 1) {
            T[] newArr = (T[]) new Comparable[2 * backingArray.length];
            for (int i = 1; i <= size; i++) {
                newArr[i] = backingArray[i];
            }
            backingArray = newArr;
        }
        if (size == 0) {
            size++;
            backingArray[size] = data;
        } else {
            size++;
            backingArray[size] = data;
            rAdd(backingArray, size);
        }
    }

    /**
     * Helper recursive method that sort added data in the right order in Maxheap backingArray.
     * @param backingArray backingArray used to store data of heap
     * @param i index of data that is comparing with its parent at index i / 2
     */
    private void rAdd(T[] backingArray, int i) {
        int largerIndex = i;
        int parentIndex = i / 2;
        if (parentIndex > 0 && backingArray[largerIndex].compareTo(backingArray[parentIndex]) > 0) {
            T temp = backingArray[parentIndex];
            backingArray[parentIndex] = backingArray[largerIndex];
            backingArray[largerIndex] = temp;
            rAdd(backingArray, parentIndex);
        }
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty.");
        }
        T output = backingArray[1];
        if (size == 1) {
            backingArray[1] = null;
            size--;
            return output;
        }
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        int curr = 1;
        T swap = backingArray[1];
        if (this.size == 1) {
            return output;
        } else {
            int larger = curr;
            boolean stop = false;
            while (curr * 2 <= size && !stop) {
                int left = 2 * curr;
                int right = 2 * curr + 1;
                boolean changed = false;
                if (left <= size && backingArray[left].compareTo(backingArray[larger]) > 0) {
                    larger = left;
                    changed = true;
                }
                if (right <= size && backingArray[right].compareTo(backingArray[larger]) > 0) {
                    larger = right;
                    changed = true;
                }
                if (changed) {
                    backingArray[curr] = backingArray[larger];
                    backingArray[larger] = swap;
                    curr = larger;
                } else {
                    stop = true;
                }
            }
        }
        return output;
    }

    /*public T remove1() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty.");
        }
        T output = backingArray[1];
        if (size == 1) {
            backingArray[1] = null;
            size--;
            return output;
        }
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        rRemove(backingArray, 1);
        return output;
    }
     private void rRemove(T[] backingArray, int i) {
        int largerIndex = i;
        int left = 2 * i;
        int right = 2 * i + 1;
        if (left <= size && backingArray[left].compareTo(backingArray[largerIndex]) > 0) {
            largerIndex = left;
        }
        if (right <= size && backingArray[right].compareTo(backingArray[largerIndex]) > 0) {
            largerIndex = right;
        }
        if (largerIndex != i) {
            T swap = backingArray[i];
            backingArray[i] = backingArray[largerIndex];
            backingArray[largerIndex] = swap;
            rRemove(backingArray, largerIndex);
        }
     }*/

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty.");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        T[] newArr = (T[]) new Comparable[INITIAL_CAPACITY];
        backingArray = newArr;
        this.size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}