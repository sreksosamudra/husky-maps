package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sequential search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class SequentialSearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> elements;

    /**
     * Constructs an empty instance.
     */
    public SequentialSearchAutocomplete() {
        elements = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        elements.addAll(terms);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        //Make an empty ArrayList
        List<CharSequence> result = new ArrayList<>();
        //Check if the prefix we want is empty, if so return the empty 'result'
        if (prefix == null || prefix.length() == 0) {
            return result;
        }

        for (CharSequence term : elements) {
            // Check if there is a term that matches the prefix
            if (Autocomplete.isPrefixOf(prefix, term)) {
                // add the term to the result
                result.add(term);
            }
        }

        return result;
    }
}
