import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
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
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        CircularSinglyLinkedListNode<T> newNode;
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Cannot add data to index that is out of list's bound.");
        } else if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to list");
        } else if (size == 0) {
            newNode = new CircularSinglyLinkedListNode<>(data);
            head = newNode;
            newNode.setNext(head);
            size++;
        } else if (index == 0) {
            newNode = new CircularSinglyLinkedListNode<>(data);
            newNode.setNext(head.getNext());
            head.setNext(newNode);
            head.getNext().setData(head.getData());
            head.setData(data);
            size++;
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            newNode = new CircularSinglyLinkedListNode<>(data);
            int count = 0;
            while (count < index - 1) {
                curr = curr.getNext();
                count++;
            }
            newNode.setNext(curr.getNext());
            curr.setNext(newNode);
            size++;
        }
    }


    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        CircularSinglyLinkedListNode<T> newNode = null;
        if (data == null) {
            throw new IllegalArgumentException("Cannot add a null data to the front of the list.");
        } else if (size == 0) {
            newNode = new CircularSinglyLinkedListNode<>(data);
            head = newNode;
            newNode.setNext(head);
            size++;
        } else {
            addAtIndex(0, data);
        }
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        CircularSinglyLinkedListNode<T> newNode = null;
        if (data == null) {
            throw new IllegalArgumentException("Cannot add a null data to the back of the list.");
        } else if (size == 0) {
            newNode = new CircularSinglyLinkedListNode<>(data);
            head = newNode;
            newNode.setNext(head);
            size++;
        } else {
            addAtIndex(size, data);
        }
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        T removed;
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Cannot remove data at index that is out of list's bound.");
        } else if (size == 1) {
            removed = head.getData();
            head = null;
            size--;
            return removed;
        } else if (index == 0) {
            removed = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
            size--;
            return removed;
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            removed = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
            size--;
            return removed;
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        CircularSinglyLinkedListNode<T> removed = null;
        if (size == 0) {
            throw new NoSuchElementException("The list is empty so no element could be removed.");
        } else {
            return removeAtIndex(0);
        }
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        CircularSinglyLinkedListNode<T> removed = null;
        CircularSinglyLinkedListNode<T> curr = head;
        if (size == 0) {
            throw new NoSuchElementException("The list is empty so no element could be removed.");
        } else {
            return removeAtIndex(size - 1);
        }
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot access data of index out of list's bound.");
        } else if (this.isEmpty()) {
            return null;
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        }
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
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        CircularSinglyLinkedListNode<T> curr = head;
        T removed = null;
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data");
        } else if (size == 0) {
            throw new NoSuchElementException("The data is not in the list since list is empty.");
        } else if (size == 1) {
            if (head.getData() == data) {
                removed = head.getData();
                head = null;
                size--;
                return removed;
            } else {
                throw new NoSuchElementException("The data is not found in the list");
            }
        } else {
            for (int i = 0; i < size - 1; i++) {
                if (curr.getNext().getData() == data) {
                    removed = curr.getNext().getData();
                }
                if (i < size - 2) {
                    curr = curr.getNext();
                }
            }
            if (removed != null) {
                curr.setNext(curr.getNext().getNext());
            } else if (removed == null && head.getData() == data) {
                removed = head.getData();
                head = head.getNext();
            } else if (removed == null && head.getData() != data) {
                throw new NoSuchElementException("The data is not found in the list.");
            }
            size--;
            return removed;
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        Object[] arr = new Object[size];
        CircularSinglyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            arr[i] = curr.getData();
            curr = curr.getNext();
        }
        return (T[]) arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
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
        // DO NOT MODIFY!
        return size;
    }
}