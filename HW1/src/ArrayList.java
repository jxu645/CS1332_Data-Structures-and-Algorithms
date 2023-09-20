import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayList.
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
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Cannot add element at positions that is "
                + "not larger than 0 and smaller than list size");
        } else if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the list.");
        } else if (size == 0 && index == 0) {
            backingArray[0] = data;
            size++;
        } else if (size == backingArray.length) {
            T[] newArr = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < index; i++) {
                newArr[i] = backingArray[i];
            }
            newArr[index] = data;
            for (int i = size; i > index; i--) {
                newArr[i] = backingArray[i - 1];
            }
            backingArray = newArr;
            size++;
        } else {
            for (int i = size; i > index; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[index] = data;
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the list.");
        } else if (size == 0) {
            backingArray[0] = data;
            size++;
        } else if (size == backingArray.length) {
            T[] newArr = (T[]) new Object[backingArray.length * 2];
            for (int i = size; i > 0; i--) {
                newArr[i] = backingArray[i - 1];
            }
            newArr[0] = data;
            backingArray = newArr;
            size++;
        }  else {
            for (int i = size; i > 0; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[0] = data;
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the list.");
        } else if (size == backingArray.length) {
            T[] newArr = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                newArr[i] = backingArray[i];
            }
            backingArray = newArr;
        }
        backingArray[size] = data;
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        T remove;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot remove element at positions that is "
                + "not larger than 0 and smaller than list size");
        } else if (index == size - 1) {
            remove = backingArray[index];
            backingArray[index] = null;
            size--;
            return remove;
        } else {
            remove = backingArray[index];
            for (int i = index; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
            backingArray[size - 1] = null;
            size--;
            return remove;
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove element at front since the list is empty.");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove element at back since the list is empty.");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        outOfBoundCheck(index);
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        T[] clear = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
        backingArray = clear;
    }

    /**
     * Returns the backing array of the list.
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
     * Returns the size of the list.
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

    /**
     * Checks if the index accessed is out of array's bound.
     *
     * @param index the index of element to be checked
     */
    private void outOfBoundCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index accessed cannot be less than 0 or larger than ArrayList size.");
        }
    }
}