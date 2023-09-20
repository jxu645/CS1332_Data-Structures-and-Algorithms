import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("List cannot be null");
        }
        for (T item: data) {
            if (item == null) {
                throw new IllegalArgumentException("Data in list cannot be null");
            }
            add(item);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
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
     * Helper recursive method that adds the data and update the tree.
     * @param curr pointer node that go through the tree
     * @param data data to be added
     * @return balanced tree after added the data
     */
    private AVLNode<T> rAdd(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<T>(data);
        } else if (curr.getData().equals(data)) {
            return curr;
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        updateNode(curr);
        return balance(curr);
    }

    /**
     * Helper method that balances the tree based on current node's balance factor.
     * @param curr node to be look at
     * @return balanced node
     */
    private AVLNode<T> balance(AVLNode<T> curr) {
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(leftRotation(curr.getLeft()));
            }
            curr = rightRotation(curr);
        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rightRotation(curr.getRight()));
            }
            curr = leftRotation(curr);
        }
        return curr;
    }

    /**
     * Helper method that determines the height of subtree after adding.
     * @param node node to get height from
     * @return node's height
     */
    private int height(AVLNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return node.getHeight();
        }
    }

    /**
     * Helper method that updates node's height and balance factor.
     * @param node node to be updated
     */
    private void updateNode(AVLNode<T> node) {
        node.setBalanceFactor(height(node.getLeft()) - height(node.getRight()));
        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);
    }

    /**
     * Helper method that do a left rotation.
     * @param curr pivot point to do the left rotation
     * @return rotated new node with new value
     */
    private AVLNode<T> leftRotation(AVLNode<T> curr) {
        AVLNode<T> a = curr.getRight();
        AVLNode<T> b = a.getLeft();
        a.setLeft(curr);
        curr.setRight(b);
        updateNode(curr);
        updateNode(a);
        return a;
    }

    /**
     * Helper method that do a right rotation.
     * @param curr pivot point to do the right rotation
     * @return rotated new node with new value
     */
    private AVLNode<T> rightRotation(AVLNode<T> curr) {
        AVLNode<T> a = curr.getLeft();
        AVLNode<T> b = a.getRight();
        a.setRight(curr);
        curr.setLeft(b);
        updateNode(curr);
        updateNode(a);
        return a;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to be removed cannot be null.");
        }
        AVLNode<T> remove = new AVLNode<>(null);
        root = rRemove(root, data, remove);
        return remove.getData();
    }

    /**
     * Helper recursive method that replaces the removed node based on children info.
     * @param curr pointer node that stops at node to removed
     * @param data data to be removed
     * @param remove node that stores the datd find to be removed
     * @return new balanced tree without data to be removed
     */
    private AVLNode<T> rRemove(AVLNode<T> curr, T data, AVLNode<T> remove) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in tree.");
        }
        int compare = data.compareTo(curr.getData());
        if (compare < 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, remove));
        } else if (compare > 0) {
            curr.setRight(rRemove(curr.getRight(), data, remove));
        } else {
            size--;
            remove.setData(curr.getData());
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                AVLNode<T> dummy = new AVLNode<>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), dummy));
                curr.setData(dummy.getData());
            }
        }
        updateNode(curr);
        return balance(curr);
    }

    /**
     * Helper method that finds the predecessor of data to be removed.
     * @param curr node passed in to find its predecessor
     * @param dummy dummy node that stores predecessor's data
     * @return curr's predecessor's data
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(removePredecessor(curr.getRight(), dummy));
        }
        updateNode(curr);
        return balance(curr);
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to get cannot be null.");
        }
        if (root == null) {
            throw new NoSuchElementException("Tree is empty.");
        }
        AVLNode<T> node = rGet(root, data);
        if (node == null) {
            throw new NoSuchElementException("Data is not in the tree.");
        } else {
            return node.getData();
        }
    }

    /**
     * Helper recursive method to find the data to get.
     * @param curr pointer node that go through the tree
     * @param data data to get
     * @return curr with data got in the tree
     */
    private AVLNode<T> rGet(AVLNode<T> curr, T data) {
        if (curr == null) {
            return null;
        }
        if (data.compareTo(curr.getData()) > 0) {
            return rGet(curr.getRight(), data);
        } else if (data.compareTo(curr.getData()) < 0) {
            return rGet(curr.getLeft(), data);
        } else {
            return curr;
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        return rContains(root, data);
    }

    /**
     * Helper recursive method that finds if data is in the tree.
     * @param curr pointer that go through the tree
     * @param data data to find
     * @return true if data is in the tree, if not, return false
     */
    private boolean rContains(AVLNode<T> curr, T data) {
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
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * This must be done recursively.
     *
     * Your list should not have duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> list = new ArrayList<>();
        rDeepestBranches(root, list);
        return list;
    }

    /**
     * Helper recursive method that finds deepest branches based on height info of nodes.
     * @param curr pointer node to look for the height, go through the tree
     * @param list list stored with deepest branches' data, using preorder traversal
     */
    private void rDeepestBranches(AVLNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            list.add(curr.getData());
            if (curr.getLeft() != null && curr.getHeight() - curr.getLeft().getHeight() <= 1) {
                rDeepestBranches(curr.getLeft(), list);
            }
            if (curr.getRight() != null && curr.getHeight() - curr.getRight().getHeight() <= 1) {
                rDeepestBranches(curr.getRight(), list);
            }
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * This must be done recursively.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     * @throws IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null || data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("Data cannot be null or data1 "
                    + "must be smaller than data2.");
        }
        List<T> list = new ArrayList<>();
        if (data1.equals(data2)) {
            return list;
        } else {
            rSortedInBetween(data1, data2, root, list);
        }
        return list;
    }

    /**
     * Helper recursive method that adds desired data using inorder traversal.
     * @param data1 lower bound of list
     * @param data2 upper bound of list
     * @param curr pointer node that access the desired nodes
     * @param list list with data that are in data1 & 2's bounds, inorder traversal.
     */
    private void rSortedInBetween(T data1, T data2, AVLNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            int compare1 = curr.getData().compareTo(data1);
            int compare2 = curr.getData().compareTo(data2);
            if (compare1 > 0) {
                rSortedInBetween(data1, data2, curr.getLeft(), list);
            }
            if (compare1 > 0 && compare2 < 0) {
                list.add(curr.getData());
            }
            if (compare2 < 0) {
                rSortedInBetween(data1, data2, curr.getRight(), list);
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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