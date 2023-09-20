import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator cannot be null.");
        }
        for (int i = arr.length - 1; i > 0; i--) {
            int maxIndex = i;
            for (int j = i - 1; j >= 0; j--) {
                if (comparator.compare(arr[j], arr[maxIndex]) > 0) {
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                T temp = arr[i];
                arr[i] = arr[maxIndex];
                arr[maxIndex] = temp;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator cannot be null.");
        }
        boolean swapped = true;
        int leftIndex = 0;
        int rightIndex = arr.length - 1;
        while (leftIndex < rightIndex && swapped) {
            swapped = false;
            int rightTrack = rightIndex - 1;
            for (int i = leftIndex; i < rightIndex; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                    rightTrack = i;
                }
            }
            if (!swapped) {
                break;
            } else {
                swapped = false;
                rightIndex = rightTrack;
                int leftTrack = leftIndex + 1;
                for (int j = rightIndex; j > leftIndex; j--) {
                    if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                        T temp = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = temp;
                        swapped = true;
                        leftTrack = j;
                    }
                }
                leftIndex = leftTrack;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator cannot be null.");
        }
        if (arr.length <= 1) {
            return;
        }
        int mid = arr.length / 2;
        T[] left = (T[]) new Object[mid];
        T[] right = (T[]) new Object[arr.length - mid];
        for (int i = 0, j = 0; i < arr.length; i++) {
            if (i < mid) {
                left[i] = arr[i];
            } else {
                right[j] = arr[i];
                j++;
            }
        }
        mergeSort(left, comparator);
        mergeSort(right, comparator);
        int leftIndex = 0;
        int rightIndex = 0;
        int arrIndex = 0;
        while (leftIndex < left.length && rightIndex < right.length) {
            if (comparator.compare(left[leftIndex], right[rightIndex]) <= 0) {
                arr[arrIndex] = left[leftIndex];
                leftIndex++;
            } else {
                arr[arrIndex] = right[rightIndex];
                rightIndex++;
            }
            arrIndex++;
        }
        while (leftIndex < left.length) {
            arr[arrIndex] = left[leftIndex];
            leftIndex++;
            arrIndex++;
        }
        while (rightIndex < right.length) {
            arr[arrIndex] = right[rightIndex];
            rightIndex++;
            arrIndex++;
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Arr or comparator or rand cannot be null");
        }
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("K is not in the range");
        }
        return rKthSelect(k, arr, comparator, rand, 0, arr.length);
    }

    /**
     * Helper recursive method for kthSelect.
     * @param k order of the smallest item in the list to return
     * @param arr array that should be modified after the method is finished executing the method
     * @param comparator the comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param low lower bound of data to look at
     * @param high higher bound of data to look at
     * @return the kth smallest element
     * @param <T> data type to sort
     */
    private static <T> T rKthSelect(int k, T[] arr, Comparator<T> comparator, Random rand, int low, int high) {
        int pivotIndex = rand.nextInt(high - low) + low;
        T pivot = arr[pivotIndex];
        arr[pivotIndex] = arr[low];
        arr[low] = pivot;
        int i = low + 1;
        int j = high - 1;
        while (j >= i) {
            while (j >= i && comparator.compare(pivot, arr[i]) >= 0) {
                i++;
            }
            while (j >= i && comparator.compare(pivot, arr[j]) <= 0) {
                j--;
            }
            if (j >= i) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        arr[low] = arr[j];
        arr[j] = pivot;
        if (j == k - 1) {
            return pivot;
        } else if (j < k - 1) {
            return rKthSelect(k, arr, comparator, rand, j + 1, high);
        } else {
            return rKthSelect(k, arr, comparator, rand, low, j);
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        } else if (arr.length <= 1 && arr.length >= 0) {
            return;
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        boolean cont = true;
        int iteration = 1;
        while (cont) {
            cont = false;
            for (int element: arr) {
                int bucketIndex = (element / iteration) % 10;
                if (element / iteration / 10 != 0) {
                    cont = true;
                }
                if (buckets[bucketIndex + 9] == null) {
                    buckets[bucketIndex + 9] = new LinkedList<Integer>();
                }
                buckets[bucketIndex + 9].add(element);
            }
            int arrIndex = 0;
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    for (int element : buckets[i]) {
                        arr[arrIndex] = element;
                        arrIndex++;
                    }
                    buckets[i] = null;
                }
            }
            iteration = iteration * 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>(data);
        int[] arr = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = pq.remove();
        }
        return arr;
    }
}