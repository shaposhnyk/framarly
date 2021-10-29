package com.sh.mmrly.rules;

import com.sh.mmrly.Corrector;
import com.sh.mmrly.Replacement;
import com.sh.mmrly.nlp.TextWithWhitespace;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Test version of corrector
 */
@ApplicationScoped
public class CorrectorImpl implements Corrector {
  @Override
  public TextWithSuggestions makeSuggestions(String text) {
    return TextWithSuggestions.simpleTextOf(text).withSuggestions(
        Suggestion.singleChangeOf(Replacement.of(0, "That"), 0),
        Suggestion.singleChangeOf(Replacement.of(0, "That"), 0),
        Suggestion.singleChangeOf(Replacement.of(0, "That"), 0)
    );
  }

  @Override
  public TextWithSuggestions makeSuggestions(List<TextWithWhitespace> sentences) {
    return null;
  }

  @Override
  public TextWithSuggestions applySuggestion(List<TextWithWhitespace> sentence, Suggestion suggestion) {
    return null;
  }
}
