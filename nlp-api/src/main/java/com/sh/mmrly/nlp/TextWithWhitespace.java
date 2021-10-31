package com.sh.mmrly.nlp;

public interface TextWithWhitespace {
  String text();

  String whitespace();

  default String completeText() {
    return text() + whitespace();
  }
}
