import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");

        }
        for (T item : data) {
            if (item == null) {
                throw new IllegalArgumentException("Data cannot be null.");
            }
            add(item);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        root = rAdd(root, data);
    }

    /**
     * Helper method to add data recursively into the tree.
     * @param curr pointer that eventually will point at node that has space to add a child
     * @param data the data to add
     * @return parent node curr with newly added child
     */
    private BSTNode<T> rAdd(BSTNode<T> curr, T data) {
        if (curr == null) {
            BSTNode<T> newNode = new BSTNode<>(data);
            size++;
            return newNode;
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to be removed cannot be null.");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = rRemove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Helper method that removes data recursively.
     * @param curr pointer that points at data to be removed
     * @param data data to be removed
     * @param dummy stores data to be removed
     * @return new arranged tree with correct order
     */
    private BSTNode<T> rRemove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        if (curr.getData().compareTo(data) < 0) {
            curr.setRight(rRemove(curr.getRight(), data, dummy));
            return curr;
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, dummy));
        } else {
            dummy.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                size--;
                return null;
            } else if (curr.getLeft() == null) {
                size--;
                return curr.getRight();
            } else if (curr.getRight() == null) {
                size--;
                return curr.getLeft();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
                size--;
            }
        }
        return curr;
    }

    /**
     * Helper method that finds the successor of the tree.
     * @param curr pointer node used to find successor
     * @param dummy2 node saves data to be replaced
     * @return node that replaces curr's right in rRemove
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy2) {
        if (curr.getLeft() == null) {
            dummy2.setData(curr.getData());
            if (curr.getRight() != null) {
                curr = curr.getRight();
                return curr;
            }
            return null;
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy2));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to be find cannot be null.");
        }
        BSTNode<T> get;
        get = rGet(root, data);
        return get.getData();
    }

    /**
     * Helper recursive method that go through one branch of the tree to get data.
     * @param curr current data to  look at
     * @param data data to be searched
     * @return data found in the tree, if not found, throw error.
     */
    private BSTNode<T> rGet(BSTNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("The data is not in the tree.");
        }
        if (curr.getData().compareTo(data) == 0) {
            return curr;
        } else if (curr.getData().compareTo(data) < 0) {
            return rGet(curr.getRight(), data);
        } else {
            return rGet(curr.getLeft(), data);
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to be searched cannot be null.");
        }
        return rContains(root, data);
    }

    /**
     * Helper recursive method to check if tree contains data.
     * @param curr pointer node that will go through approximately half of tree
     * @param data data to be checked
     * @return boolean value that shows if value contains in the tree
     */
    private boolean rContains(BSTNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }
        if (curr.getData().compareTo(data) < 0) {
            return rContains(curr.getRight(), data);
        } else if (curr.getData().compareTo(data) > 0) {
            return rContains(curr.getLeft(), data);
        } else {
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> out = new ArrayList<>();
        if (root == null) {
            return out;
        }
        rPreorder(root, out);
        return out;
    }

    /**
     * Helper recursive method that puts data in the tree in preorder.
     * @param curr pointer node that go through the tree
     * @param out list with tree elements in preorder
     */
    private void rPreorder(BSTNode<T> curr, List<T> out) {
        if (curr != null) {
            out.add(curr.getData());
            rPreorder(curr.getLeft(), out);
            rPreorder(curr.getRight(), out);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> out = new ArrayList<>();
        if (root == null) {
            return out;
        }
        rInorder(root, out);
        return out;
    }

    /**
     * Helper recursive method that puts data in the tree in inorder.
     * @param curr pointer node that go through the tree
     * @param out list with tree elements in inorder
     */
    private void rInorder(BSTNode<T> curr, List<T> out) {
        if (curr != null) {
            rInorder(curr.getLeft(), out);
            out.add(curr.getData());
            rInorder(curr.getRight(), out);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> out = new ArrayList<>();
        if (root == null) {
            return out;
        }
        rPostorder(root, out);
        return out;
    }

    /**
     * Helper recursive method that puts data in the tree in postorder.
     * @param curr pointer node that go through the tree
     * @param out list with tree elements in postorder
     */
    private void rPostorder(BSTNode<T> curr, List<T> out) {
        if (curr != null) {
            rPostorder(curr.getLeft(), out);
            rPostorder(curr.getRight(), out);
            out.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> out = new ArrayList<>();
        if (root == null) {
            return out;
        }
        Queue<BSTNode<T>> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> node = q.poll();
            if (node != null) {
                out.add(node.getData());
                if (node.getLeft() != null) {
                    q.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    q.add(node.getRight());
                }
            }
        }
        return out;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return rHeight(root);
    }

    /**
     * Helper recursive method that calculates height of left node and right node.
     * @param curr pointer that go through tree, stop at the leaves of tree.
     * @return integer of the calculated height until root
     */
    private int rHeight(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        int lHeight = rHeight(curr.getLeft());
        int rHeight = rHeight(curr.getRight());
        return Math.max(lHeight, rHeight) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("Cannot return data that is larger than the tree size"
                    + " or that is at position less than 0.");
        }
        List<T> out = new ArrayList<>();
        rKLargest(root, k, out);
        return out;
    }

    /**
     * Helper recursive method that go through tree in reversed inorder.
     * @param curr pointer node that reach to rightmost node first
     * @param k numbers of node to be added
     * @param out list that contains added nodes
     */
    private void rKLargest(BSTNode<T> curr, int k, List<T> out) {
        if (curr != null) {
            rKLargest(curr.getRight(), k, out);
            if (out.size() < k) {
                out.add(0, curr.getData());
            }
            rKLargest(curr.getLeft(), k, out);
        }
    }

    public List<T> kLargest1(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("Cannot return data that is larger than the tree size"
                    + " or that is at position less than 0.");
        }
        List<T> out = new LinkedList<>();
        rKLargest1(root, k, (LinkedList<T>) out);
        return out;
    }

    private void rKLargest1(BSTNode<T> curr, int k, LinkedList<T> out) {
        if (curr == null || out.size() == k) {
            return;
        }
        rKLargest(curr.getRight(), k, out);
        if (out.size() < k) {
            out.addFirst(curr.getData());
        }
        rKLargest(curr.getLeft(), k, out);
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}