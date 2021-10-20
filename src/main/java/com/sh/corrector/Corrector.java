package com.sh.corrector;

import com.sh.nlp.TextWithWhitespace;
import com.sh.rules.Suggestion;
import com.sh.rules.TextWithSuggestions;

import java.util.List;

public interface Corrector {
  List<List<TextWithWhitespace>> tokenize(String text);

  List<TextWithSuggestions> makeSuggestions(String text);

  List<TextWithSuggestions> makeSuggestions(List<List<TextWithWhitespace>> sentences);

  List<TextWithWhitespace> applySuggestion(List<TextWithWhitespace> sentence, Suggestion suggestion);
}
