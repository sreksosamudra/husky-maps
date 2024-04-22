package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    @Override
    public void addAll( Collection<? extends CharSequence> terms) {
        // Check if the sequence is empty or null
        if (terms == null || terms.isEmpty()) {
            throw new IllegalArgumentException("null terms not acceptable");
        }

        // Loop through each term in the sequence to add each term's characters
        for (CharSequence term : terms) {
            if (term == null) {
                throw new IllegalArgumentException("null terms not acceptable");
            }
            overallRoot = add(overallRoot, term, 0);
        }

    }

    private Node add(Node root, CharSequence term, int i) {
        char c = term.charAt(i);

        if (root == null) {
            root = new Node(c);
        }

        if      (c < root.data)               root.left  = add(root.left,  term, i);
        else if (c > root.data)               root.right = add(root.right, term, i);
        else if (i < term.length() - 1)       root.mid   = add(root.mid,   term, i+1);
        else                                  root.isTerm = true;
        return root;
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {

        //Make an empty ArrayList
        List<CharSequence> result = new ArrayList<>();

        //Check if the prefix we want or the tree root is empty, if so return the empty 'result'
        if (prefix == null || prefix.length() == 0 || overallRoot == null) {
            return result;
        }

        Node x = get(overallRoot, prefix, 0);

        if (x == null) {
            return result;
        }
        if (x.isTerm) {
            result.add(prefix);
        }
        collect(x.mid, new StringBuilder(prefix), result);
        return result;
    }

    private Node get(Node x, CharSequence key, int i) {
        if (x == null) return null;
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        char c = key.charAt(i);
        if      (c < x.data)              return get(x.left,  key, i);
        else if (c > x.data)              return get(x.right, key, i);
        else if (i < key.length() - 1)    return get(x.mid,   key, i+1);
        else                              return x;
    }

    private void collect(Node x, StringBuilder prefix, List<CharSequence> result) {
        if (x == null) {
            return;
        }
        collect(x.left, new StringBuilder(prefix), result);
        if (x.isTerm) {
            result.add(prefix.toString() + x.data);
        }
        collect(x.mid, prefix.append(x.data), result);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, new StringBuilder(prefix), result);
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}

// hellooooooooooooo