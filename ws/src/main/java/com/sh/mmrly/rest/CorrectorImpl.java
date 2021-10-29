package com.sh.mmrly.rest;

import com.sh.mmrly.Corrector;
import com.sh.mmrly.nlp.TextWithWhitespace;
import com.sh.mmrly.rules.Suggestion;
import com.sh.mmrly.rules.TextWithSuggestions;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CorrectorImpl implements Corrector {
  @Override
  public TextWithSuggestions makeSuggestions(String text) {
    return TextWithSuggestions.simpleTextOf("This is a text.");
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
