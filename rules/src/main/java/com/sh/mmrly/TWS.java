package com.sh.mmrly;

import com.sh.mmrly.nlp.TextWithWhitespace;

public record TWS(String text, String whitespace) implements TextWithWhitespace {
  public static TextWithWhitespace of(String text, String space) {
    return new TWS(text, space);
  }

  public static TextWithWhitespace textOf(String text) {
    return new TWS(text, "");
  }

  public static TextWithWhitespace textWithSpaceOf(String text) {
    return new TWS(text, " ");
  }

  public static TextWithWhitespace copyOf(TextWithWhitespace original) {
    return of(original.text(), original.whitespace());
  }
}
