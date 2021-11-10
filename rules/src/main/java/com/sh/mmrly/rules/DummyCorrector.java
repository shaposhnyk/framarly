package com.sh.mmrly.rules;

import com.sh.mmrly.*;
import com.sh.mmrly.nlp.TextWithWhitespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test version of corrector. No spacy involved
 */
public class DummyCorrector implements Corrector {
  private static final Logger logger = LoggerFactory.getLogger(DummyCorrector.class);

  @Override
  public TextWithSuggestions makeSuggestions(String text) {
    TextWithSuggestions txt = TextWithSuggestions.simpleTextOf(text);
    List<TextWithMarkup> sentence = txt.sentence();
    if (sentence.size() < 4) {
      logger.info("Text should be longer than 3 characters, was: {}", text);
      return txt;
    }
    var replacement = Replacement.replaceAt(1, "La");
    var rep = "Replace '" + sentence.get(replacement.startIdx()).text() + "' with '" + replacement.replacementText().trim() + "'";

    Replacement insert = Replacement.insertAt(1, "bon");
    var ins = "Insert '" + insert.replacementText().trim() + "' after first word";
    var del = "Delete '" + sentence.get(1).completeText() + sentence.get(2).text() + "'";
    return txt.withSuggestions(
        Suggestion.changeOf(rep, replacement, 0),
        Suggestion.changeOf(ins, insert, 0),
        Suggestion.changeOf(del, Replacement.deleteFrom(1, 2), 0)
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
  public TextWithSuggestions applySuggestion(List<? extends TextWithWhitespace> sentence, Suggestion suggestion) {
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
