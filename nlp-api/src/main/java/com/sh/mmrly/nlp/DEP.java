package com.sh.mmrly.nlp;

import org.jetbrains.annotations.NotNull;

public record DEP(@NotNull String ref) {
  public static final DEP ROOT = new DEP("ROOT");
  public static final DEP nsubj = new DEP("nsubj");
  public static final DEP tense = new DEP("aux:tense");
  public static final DEP OTHER = new DEP("other");

  public static DEP valueOf(String value) {
    return value == null ? null : new DEP(value);
  }
}
