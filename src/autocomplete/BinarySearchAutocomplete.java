package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.*;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> elements;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        elements = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        //Add all the terms into the 'elements'
        elements.addAll(terms);
        //Sort the new ArrayList with the new term(s)
        Collections.sort(elements, CharSequence::compare);  //sorted array

    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        //Make an empty ArrayList
        List<CharSequence> result = new ArrayList<>();
        //Check if the prefix we want is empty, if so return the empty 'result'
        if (prefix == null || prefix.length() == 0) {
            return result;
        }
        //Find the insertion point
        //binarySearch returns insertion point if prefix is found
        int start = Collections.binarySearch(elements, prefix, CharSequence::compare);
        //else, it returns (-(insertion point) - 1) so revert it back
        if (start < 0) {
                start = -(start + 1);
        }
        //Store all terms with the same prefix in 'result'
        for (CharSequence term : elements.subList(start, elements.size())) {
            if (Autocomplete.isPrefixOf(prefix, term)) {
                result.add(term);
            } else {
                return result;
            }
        }
        return result;
    }


}
