package com.sh.mmrly.nlp;

import java.util.List;

public interface LanguageProcessing {
  XResponse<List<String>> splitSentences(String text);

  XResponse<List<TextWithPos>> pos(String text);
}
