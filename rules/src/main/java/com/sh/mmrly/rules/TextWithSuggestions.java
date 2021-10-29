package com.sh.mmrly.rules;

import com.sh.mmrly.nlp.TextWithWhitespace;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record TextWithSuggestions(
    List<TextWithWhitespace> sentence, // sentence tokens
    List<Suggestion> suggestions // suggestions
) {
  public static TextWithSuggestions simpleTextOf(String text) {
    final List<String> parts = Arrays.asList(text.split(" "));
    var len = parts.size();
    final List<TextWithWhitespace> sentence = Stream.concat(
        parts.subList(0, Math.max(0, len - 1)).stream().map(TextWithSuggestions::textWithSpaceOf),
        parts.subList(Math.max(0, len - 1), len).stream().map(TextWithSuggestions::textOf)
    ).collect(Collectors.toUnmodifiableList());
    return new TextWithSuggestions(sentence, List.of());
  }

  public TextWithSuggestions withSuggestions(Suggestion... newSuggestions) {
    return new TextWithSuggestions(sentence, Arrays.asList(newSuggestions));
  }

  public String text() {
    return sentence.stream()
        .map(TextWithWhitespace::completeText)
        .collect(Collectors.joining());
  }

  @Override
  public String toString() {
    return "TextWithSuggestions{" +
        "sentence=" + sentence +
        ", suggestions=" + suggestions +
        '}';
  }

  public static TextWithWhitespace textWithSpaceOf(String text) {
    return new TWS(text, " ");
  }

  public static TextWithWhitespace textOf(String text) {
    return new TWS(text, "");
  }

  record TWS(String text, String whitespace) implements TextWithWhitespace {

  }
}
