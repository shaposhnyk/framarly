package com.sh.mmrly.nlp;

import java.util.List;

public interface Tokenizer {
  XResponse<List<TextWithPos>> tokenize(String text);
}
