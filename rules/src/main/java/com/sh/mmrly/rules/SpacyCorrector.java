package com.sh.mmrly.rules;

import com.sh.mmrly.Corrector;
import com.sh.mmrly.Replacement;
import com.sh.mmrly.RuleChecker;
import com.sh.mmrly.TWS;
import com.sh.mmrly.nlp.ParsedSentence;
import com.sh.mmrly.nlp.TextWithWhitespace;
import com.sh.mmrly.nlp.Tokenizer;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Spacy-based corrector
 */
public class SpacyCorrector extends DummyCorrector implements Corrector {
  private final Tokenizer tokenizer;
  private final Iterable<RuleChecker> checkers;

  @Inject
  public SpacyCorrector(Instance<RuleChecker> checkers, Tokenizer tokenizer) {
    this(tokenizer, checkers);
  }

  /**
   * For test purposes
   */
  public SpacyCorrector(Tokenizer tokenizer, RuleChecker... checkers) {
    this(tokenizer, Arrays.asList(checkers));
  }

  /**
   * For test purposes. Order of parameters changed, so we do not have the same erasure as in CDI constructor
   */
  public SpacyCorrector(Tokenizer tokenizer, Iterable<RuleChecker> checkers) {
    this.tokenizer = tokenizer;
    this.checkers = checkers;
  }

  @Override
  public TextWithSuggestions makeSuggestions(String text) {
    final var tokenizedText = tokenizer.tokenize(text);
    final List<TextWithWhitespace> tokens = new ArrayList<>();

    List<Suggestion> suggestions = Collections.emptyList();
    for (int i = 0; i < tokenizedText.data().size(); i++) {
      ParsedSentence sentence = tokenizedText.data().get(i);
      sentence.tags().stream().map(TWS::copyOf).forEach(tokens::add);
      if (i == 0) {
        suggestions = computeSuggestions(tokens).suggestions();
      }
    }
    return new TextWithSuggestions(tokens, suggestions);
  }

  private TextWithSuggestions computeSuggestions(List<TextWithWhitespace> sentence) {
    return new TextWithSuggestions(sentence, Arrays.asList(
        Suggestion.singleChangeOf(Replacement.replaceAt(0, "That"), 0),
        Suggestion.singleChangeOf(Replacement.insertAt(0, "Oh"), 0),
        Suggestion.singleChangeOf(Replacement.deleteFrom(1, 2), 0)
    ));
  }
}
