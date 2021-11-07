package com.sh.mmrly;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Suggestion(
    @NotNull RuleCode title,
    @NotNull List<Integer> selection, // indexes of words to select
    @NotNull List<Replacement> replacements // replacements
) {
  public static Suggestion of(RuleCode rule) {
    return new Suggestion(rule, List.of(), List.of());
  }

  public static Suggestion unitarySelectionOf(RuleCode rule, int idx, Replacement... changes) {
    return new Suggestion(rule, List.of(idx), List.of(changes));
  }

  public static Suggestion selectionOf(RuleCode rule, int fromInclusive, int toExclusive, Replacement... changes) {
    List<Integer> collect = IntStream.range(fromInclusive, toExclusive).boxed().collect(Collectors.toList());
    return new Suggestion(rule, collect, List.of(changes));
  }

  public static Suggestion of(RuleCode rule, int... selection) {
    Arrays.sort(selection);
    List<Integer> collect = IntStream.of(selection).boxed().collect(Collectors.toList());
    return new Suggestion(rule, collect, List.of());
  }

  public static Suggestion changeOf(RuleCode rule, Replacement change, int... selection) {
    Arrays.sort(selection);
    List<Integer> collect = IntStream.of(selection).boxed().collect(Collectors.toList());
    return new Suggestion(rule, collect, List.of(change));
  }

  public Suggestion withSelection(int... selection) {
    Arrays.sort(selection);
    return new Suggestion(title, IntStream.of(selection).boxed().collect(Collectors.toList()), replacements);
  }

  public Suggestion withReplacements(Replacement... replacements) {
    return new Suggestion(title, selection, Arrays.asList(replacements));
  }
}
