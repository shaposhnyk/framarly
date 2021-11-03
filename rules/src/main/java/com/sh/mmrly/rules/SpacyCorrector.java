package com.sh.mmrly.rules;

import com.sh.mmrly.*;
import com.sh.mmrly.nlp.ParsedSentence;
import com.sh.mmrly.nlp.TaggedToken;
import com.sh.mmrly.nlp.TextWithWhitespace;
import com.sh.mmrly.nlp.Tokenizer;
import io.quarkus.logging.Log;

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
      Log.infov("processing sentence {0}", sentence.tags());
      if (i == 0) {
        suggestions = computeSuggestions(sentence.tags());
      }
    }
    return new TextWithSuggestions(tokens, suggestions);
  }

  private List<Suggestion> computeSuggestions(List<TaggedToken> sentence) {
    final List<Suggestion> list = new ArrayList<>();
    for (RuleChecker chk : checkers) {
      List<Suggestion> suggestions = chk.makeSuggestions(sentence);
      list.addAll(suggestions);
    }
    return list;
  }
}
