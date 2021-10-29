package com.sh.mmrly.rules;

import com.sh.mmrly.Replacement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Suggestion(
    List<Integer> selection, // indexes of words to select
    List<Replacement> replacements // replacements
) {
  public static Suggestion unitarySelectionOf(int idx, Replacement... changes) {
    return new Suggestion(List.of(idx), List.of(changes));
  }

  public static Suggestion singleSelectionOf(int fromInclusive, int toExclusive, Replacement... changes) {
    List<Integer> collect = IntStream.range(fromInclusive, toExclusive).boxed().collect(Collectors.toList());
    return new Suggestion(collect, List.of(changes));
  }

  public static Suggestion selectionOf(int... selection) {
    Arrays.sort(selection);
    List<Integer> collect = IntStream.of(selection).boxed().collect(Collectors.toList());
    return new Suggestion(collect, List.of());
  }

  public static Suggestion singleChangeOf(Replacement change, int... selection) {
    Arrays.sort(selection);
    List<Integer> collect = IntStream.of(selection).boxed().collect(Collectors.toList());
    return new Suggestion(collect, List.of(change));
  }
}
