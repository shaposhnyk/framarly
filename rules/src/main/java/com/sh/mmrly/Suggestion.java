package com.sh.mmrly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Suggestion(
    @NotNull String title,
    @Nullable SelectionType type,
    @NotNull List<Integer> selection, // indexes of words to select
    @NotNull List<Replacement> replacements // replacements
) {
  public static Suggestion of(String rule) {
    return new Suggestion(rule, SelectionType.ERROR, List.of(), List.of());
  }

  public static Suggestion unitarySelectionOf(String rule, int idx, Replacement... changes) {
    return new Suggestion(rule, SelectionType.ERROR, List.of(idx), List.of(changes));
  }

  public static Suggestion selectionOf(String rule, int fromInclusive, int toExclusive, Replacement... changes) {
    List<Integer> collect = IntStream.range(fromInclusive, toExclusive).boxed().collect(Collectors.toList());
    return new Suggestion(rule, SelectionType.ERROR, collect, List.of(changes));
  }

  public static Suggestion of(String rule, int... selection) {
    Arrays.sort(selection);
    List<Integer> collect = IntStream.of(selection).boxed().collect(Collectors.toList());
    return new Suggestion(rule, SelectionType.ERROR, collect, List.of());
  }

  public static Suggestion changeOf(String rule, Replacement change, int... selection) {
    Arrays.sort(selection);
    List<Integer> collect = IntStream.of(selection).boxed().collect(Collectors.toList());
    return new Suggestion(rule, SelectionType.ERROR, collect, List.of(change));
  }

  public Suggestion withSelection(int... selection) {
    Arrays.sort(selection);
    return new Suggestion(title, type, IntStream.of(selection).boxed().collect(Collectors.toList()), replacements);
  }

  public Suggestion withReplacements(Replacement... replacements) {
    return new Suggestion(title, type, selection, Arrays.asList(replacements));
  }
}
