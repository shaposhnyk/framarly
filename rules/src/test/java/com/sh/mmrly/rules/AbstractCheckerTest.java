package com.sh.mmrly.rules;

import com.sh.mmrly.Replacement;
import com.sh.mmrly.RuleChecker;
import com.sh.mmrly.Suggestion;
import com.sh.mmrly.nlp.TaggedToken;
import com.sh.mmrly.nlp.TextWithWhitespace;
import org.assertj.core.api.AbstractListAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;

import java.util.List;

public class AbstractCheckerTest {
  public AbstractListAssert<?, List<? extends String>, String, ObjectAssert<String>> assertReplacementTxt(RuleChecker checker, List<TaggedToken> sentence) {
    return Assertions.assertThat(checker.makeSuggestions(sentence))
        .flatExtracting(Suggestion::replacements)
        .flatExtracting(Replacement::replacement)
        .extracting(TextWithWhitespace::text);
  }

  public AbstractListAssert<?, List<? extends Replacement>, Replacement, ObjectAssert<Replacement>> assertReplacement(RuleChecker checker, List<TaggedToken> sentence) {
    return Assertions.assertThat(checker.makeSuggestions(sentence))
        .flatExtracting(Suggestion::replacements);
  }

  public AbstractListAssert<?, List<? extends Integer>, Integer, ObjectAssert<Integer>> assertSelection(RuleChecker checker, List<TaggedToken> sentence) {
    return Assertions.assertThat(checker.makeSuggestions(sentence))
        .flatExtracting(Suggestion::selection);
  }

  public TaggedToken t(String text) {
    return TaggedToken.unknownOf(text);
  }
}
