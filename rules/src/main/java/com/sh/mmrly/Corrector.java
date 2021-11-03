package com.sh.mmrly;

import com.sh.mmrly.nlp.TextWithWhitespace;

import java.util.List;

public interface Corrector {
  TextWithSuggestions makeSuggestions(String text);

  TextWithSuggestions makeSuggestions(List<TextWithWhitespace> sentences);

  TextWithSuggestions applySuggestion(List<TextWithWhitespace> sentence, Suggestion suggestion);
}
