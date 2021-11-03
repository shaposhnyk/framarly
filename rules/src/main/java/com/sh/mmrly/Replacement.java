package com.sh.mmrly;

import com.sh.mmrly.nlp.TextWithWhitespace;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Unitary change. Proposed to replace positions with some text
 */
public record Replacement(
    int startIdx,
    int len,
    @NotNull List<TextWithWhitespace> replacement
) {
  public String replacementText() {
    return replacement.stream().map(TextWithWhitespace::completeText).collect(Collectors.joining());
  }

  public static Replacement of(int idx, int len, TextWithWhitespace... replacements) {
    return new Replacement(idx, len, Arrays.asList(replacements));
  }

  public static Replacement replaceAt(int idx, TextWithWhitespace... replacements) {
    return of(idx, replacements.length, replacements);
  }

  public static Replacement insertAt(int idx, TextWithWhitespace... replacements) {
    return of(idx, 0, replacements);
  }

  public static Replacement deleteFrom(int idx, int len) {
    return of(idx, len);
  }

  /**
   * Mostly for testing purposes
   *
   * @return replacement with a text with a space
   */
  public static Replacement insertAt(int idx, String... replacements) {
    TextWithWhitespace[] reps = Arrays.stream(replacements)
        .map(TWS::textWithSpaceOf)
        .toArray(TWS[]::new);
    return insertAt(idx, reps);
  }

  /**
   * Mostly for testing purposes
   *
   * @return replacement with a text with a space
   */
  public static Replacement replaceAt(int idx, String... replacements) {
    TextWithWhitespace[] reps = Arrays.stream(replacements)
        .map(TWS::textWithSpaceOf)
        .toArray(TWS[]::new);
    return replaceAt(idx, reps);
  }
}
