package com.sh.mmrly.nlp;

import org.jetbrains.annotations.NotNull;

public record MNumber(@NotNull String ref) {
  public static final MNumber SINGULAR = new MNumber("Sing");
  public static final MNumber PLURAL = new MNumber("Plur");

  public static MNumber valueOf(String value) {
    return value == null ? null : new MNumber(value);
  }
}
