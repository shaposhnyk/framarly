package com.sh.mmrly;

import com.sh.mmrly.nlp.TextWithWhitespace;
import com.sh.mmrly.rules.Suggestion;
import com.sh.mmrly.rules.TextWithSuggestions;

import java.util.List;

public interface Corrector {
  TextWithSuggestions makeSuggestions(String text);

  TextWithSuggestions makeSuggestions(List<TextWithWhitespace> sentences);

  TextWithSuggestions applySuggestion(List<TextWithWhitespace> sentence, Suggestion suggestion);
}
