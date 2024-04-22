package minpq;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * {@link PriorityQueue} implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class HeapMinPQ<E> implements MinPQ<E> {
    /**
     * {@link PriorityQueue} storing {@link PriorityNode} objects representing each element-priority pair.
     */
    private final PriorityQueue<PriorityNode<E>> pq;

    /**
     * Constructs an empty instance.
     */
    public HeapMinPQ() {
        pq = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::getPriority));
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        // adds a priority node to the priority queue
        pq.add(new PriorityNode<>(element, priority));
    }

    @Override
    public boolean contains(E element) {
        // checks if pq contains the element
        return pq.contains(new PriorityNode<>(element, 999));
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // get the head of the queue (element w/ lowest priority value)
        PriorityNode<E> min = pq.peek();
        return min.getElement();

    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }

        // get the head of the queue (element w/ lowest priority value)
        // remove the node
        return pq.poll().getElement();
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
//        for (PriorityNode<E> node : pq) {
//            if (node.getElement().equals(element)) {
//                pq.remove(node);
//                pq.add(new PriorityNode<>(element, priority));
//
//                elements.contains(new PriorityNode<>("example", 1));
//                elements.remove(new PriorityNode<>("example", 2));
//            }
//        }
        PriorityNode<E> node = new PriorityNode<>(element, priority);
        pq.contains(node);
        pq.remove(node);
        pq.add(node);


    }

    @Override
    public int size() {
        return pq.size();
    }
}
