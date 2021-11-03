package com.sh.mmrly.rules;

import com.sh.mmrly.*;
import com.sh.mmrly.nlp.TextWithWhitespace;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test version of corrector. No spacy involved
 */
public class DummyCorrector implements Corrector {
  private final RuleCode dummyRule = new RuleCode("DUMMY");

  @Override
  public TextWithSuggestions makeSuggestions(String text) {
    return TextWithSuggestions.simpleTextOf(text).withSuggestions(
        Suggestion.changeOf(dummyRule, Replacement.replaceAt(0, "That"), 0),
        Suggestion.changeOf(dummyRule, Replacement.insertAt(0, "Oh"), 0),
        Suggestion.changeOf(dummyRule, Replacement.deleteFrom(1, 2), 0)
    );
  }

  @Override
  public TextWithSuggestions makeSuggestions(List<TextWithWhitespace> sentences) {
    String text = sentences.stream()
        .map(TextWithWhitespace::completeText)
        .collect(Collectors.joining());
    return makeSuggestions(text);
  }

  @Override
  public TextWithSuggestions applySuggestion(List<TextWithWhitespace> sentence, Suggestion suggestion) {
    List<TextWithWhitespace> mutableSentence = new ArrayList<>(sentence);
    for (Replacement r : suggestion.replacements()) {
      int idx = r.startIdx();
      mutableSentence.subList(idx, idx + r.len()).clear();
      for (int i = r.replacement().size() - 1; i >= 0; i--) {
        mutableSentence.add(idx, r.replacement().get(i));
      }
    }
    return makeSuggestions(mutableSentence);
  }
}
