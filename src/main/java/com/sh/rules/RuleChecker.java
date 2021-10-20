package com.sh.rules;

import com.sh.nlp.TaggedToken;

import java.util.List;

public interface RuleChecker {
  RuleCode code();

  List<Suggestion> makeSuggestions(List<TaggedToken> sentence);
}
