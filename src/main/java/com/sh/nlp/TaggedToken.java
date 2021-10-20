package com.sh.nlp;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.IntStream;

public record TaggedToken(
    @NotNull String text,
    @NotNull String lemma,
    @NotNull POS pos,
    @NotNull DEP dep,
    @NotNull Morph morph,
    @NotNull List<Integer> children,
    @NotNull String whitespace
) implements TextWithWhitespace {
  public static TaggedToken unknownOf(String text) {
    return posOf(text, POS.UNKNOWN);
  }

  public static TaggedToken posOf(String text, POS pos) {
    return posOf(text, pos, Morphs.EMPTY);
  }

  public static TaggedToken posOf(String text, POS pos, Morph morph) {
    return new TaggedToken(text, text.toLowerCase(), pos, DEP.OTHER, morph, List.of(), " ");
  }

  public TaggedToken withChildren(int... children) {
    List<Integer> newChildren = IntStream.of(children).boxed().toList();
    return new TaggedToken(text, lemma, pos, dep, morph, newChildren, whitespace);
  }
}
