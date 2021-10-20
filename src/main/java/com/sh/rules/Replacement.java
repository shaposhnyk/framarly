package com.sh.rules;

import com.sh.nlp.TaggedToken;
import com.sh.nlp.TextWithWhitespace;

import java.util.Arrays;
import java.util.List;

/**
 * Unitary change. Proposed to replace positions with some text
 */
public record Replacement(
    List<Integer> selection,
    List<TextWithWhitespace> replacement
) {
  public static Replacement of(int idx, String... replacement) {
    final List<TextWithWhitespace> reps = Arrays.stream(replacement)
        .map(str -> (TextWithWhitespace) TaggedToken.unknownOf(str))
        .toList();
    return new Replacement(List.of(idx), reps);
  }
}
