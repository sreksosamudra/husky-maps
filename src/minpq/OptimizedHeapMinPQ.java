package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class OptimizedHeapMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of element-priority pairs.
     */
    private final List<PriorityNode<E>> elements;
    /**
     * {@link Map} of each element to its associated index in the {@code elements} heap.
     */
    private final Map<E, Integer> elementsToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        elements = new ArrayList<>();
        elementsToIndex = new HashMap<>();
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        // create new node with element & its priority
        PriorityNode<E> node = new PriorityNode<>(element, priority);
        // add new node to ArrayList
        elements.add(node);
        // add the element and its array index to HashMap
        elementsToIndex.put(element, elements.indexOf(node));
        swim(elements.indexOf(node));
    }

    @Override
    public boolean contains(E element) {
        //checks if an element exists as a key in the HashMap
        return elementsToIndex.containsKey(element);
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        //MinPQ at index 0 will always be the minimum value
        return elements.get(0).getElement();
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // get the element with lowest priority value
        E minElement = peekMin();
        // swap the element at index 0 and index (size-1)
        swap(0, elements.size() - 1);
        // remove the element from ArrayPQ
        elements.remove(elements.size() - 1);
        // remove element and index pair in HashMap
        elementsToIndex.remove(minElement);
        // sink the element at top PQ until it matches minPQ invariant
        if (!isEmpty()) {
            sink(0);
        }

        return minElement;
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        // find the index of element
        int index = elementsToIndex.get(element);
        double oldPriority = elements.get(index).getPriority();
        // change priority of the element
        elements.get(index).setPriority(priority);
        // sink/swim the element
        if (priority < oldPriority) {
            swim(index);
        } else {
            sink(index);
        }
    }

    @Override
    public int size()  {
        return elements.size();
    }

    // helper methods //
    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            swap(k/2, k);
            k = k/2;
        }
        if(greater(0, k)) {
            swap(0, k);
            k = 0;
        }
    }

    private void sink(int k) {
        while (2*k  <= elements.size() - 1) {
            int j = 2*k;
            if (j < elements.size() - 1 && greater(j, j+1)) j++;

            if (!greater(k, j)) break;
            swap(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j) {
        PriorityNode<E> nodeA = elements.get(i);
        PriorityNode<E> nodeB = elements.get(j);

        return (nodeA.getPriority() > nodeB.getPriority());
    }

    private void swap(int i, int j) {
        Collections.swap(elements, i, j);
        elementsToIndex.put(elements.get(i).getElement(), i);
        elementsToIndex.put(elements.get(j).getElement(), j);
    }


}

