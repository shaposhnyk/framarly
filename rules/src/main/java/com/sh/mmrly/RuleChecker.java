package com.sh.mmrly;

import com.sh.mmrly.nlp.TaggedToken;
import com.sh.mmrly.rules.Suggestion;

import java.util.List;

public interface RuleChecker {
  RuleCode code();

  List<Suggestion> makeSuggestions(List<TaggedToken> sentence);
}
