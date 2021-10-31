package com.sh.mmrly.nlp;

import java.util.List;

public interface Tokenizer {
  XResponse<List<ParsedSentence>> tokenize(String text);
}
