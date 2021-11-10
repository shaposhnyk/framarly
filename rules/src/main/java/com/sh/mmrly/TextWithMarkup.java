package com.sh.mmrly;

import com.sh.mmrly.nlp.TextWithWhitespace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record TextWithMarkup(
    @NotNull String text,
    @NotNull String whitespace,
    @Nullable SelectionType type
) implements TextWithWhitespace {
  public static TextWithMarkup of(TextWithWhitespace source) {
    return new TextWithMarkup(source.text(), source.whitespace(), null);
  }

  public static List<TextWithMarkup> listOf(List<TextWithWhitespace> sources) {
    return sources.stream().map(TextWithMarkup::of).collect(Collectors.toList());
  }

  public TextWithMarkup withSelectionType(@Nullable SelectionType type) {
    if (Objects.equals(type, this.type)) {
      return this;
    }
    return new TextWithMarkup(text, whitespace, type);
  }
}
