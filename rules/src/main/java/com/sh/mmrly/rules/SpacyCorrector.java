package com.sh.mmrly.rules;

import com.sh.mmrly.*;
import com.sh.mmrly.nlp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Spacy-based corrector
 */
public class SpacyCorrector extends DummyCorrector implements Corrector {
  private static final Logger logger = LoggerFactory.getLogger(SpacyCorrector.class);
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
      if (logger.isInfoEnabled()) {
        logSentence(sentence);
      }
      if (i == 0) {
        suggestions = computeSuggestions(sentence.tags());
      }
    }
    return new TextWithSuggestions(tokens, suggestions);
  }

  private void logSentence(ParsedSentence sentence) {
    logger.info("processing sentence: {}", sentence.text());
    final var tags = sentence.tags().stream()
        .map(TaggedToken::pos).map(POS::ref)
        .collect(Collectors.joining("\t"));
    logger.info("          with tags: {}", tags);
    final var morphs = sentence.tags().stream()
        .map(TaggedToken::morph)
        .map(m -> Stream.of(m.person(), m.number(), m.gender())
            .filter(Objects::nonNull)
            .map(Objects::toString).map(s -> s.substring(0, 1))
            .collect(Collectors.joining(","))
        )
        .collect(Collectors.joining("\t"));
    logger.info("        with morphs: {}", morphs);
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
