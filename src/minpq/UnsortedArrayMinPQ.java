package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class UnsortedArrayMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the element-priority pairs in no specific order.
     */
    private final List<PriorityNode<E>> elements;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        elements = new ArrayList<>();
    }


    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        elements.add(new PriorityNode<>(element, priority));
    }

    @Override
    public boolean contains(E element) {
        return elements.contains(new PriorityNode<>(element, 999));
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode<E> currentMin = elements.get(0);
        for (PriorityNode<E> element: elements){
            if(element.getPriority() < currentMin.getPriority()){
                currentMin = element;
            }
        }
        return currentMin.getElement();
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // get the element with lowest priority value
        E element = peekMin();
        // remove the element with lowest priority value (assumed its priority value is 0)
        elements.remove(new PriorityNode<>(element, 0));

        return element;
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }

        for (PriorityNode<E> node : elements) {
            if (node.getElement().equals(element)) {
                node.setPriority(priority);
            }
        }
    }

    @Override
    public int size() {
        return elements.size();
    }
}
