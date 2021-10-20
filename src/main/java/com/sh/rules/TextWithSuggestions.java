package com.sh.rules;

import com.sh.nlp.TextWithWhitespace;

import java.util.List;

public record TextWithSuggestions(
    List<TextWithWhitespace> sentence, // sentence tokens
    List<Suggestion> suggestions // suggestions
) {

}
