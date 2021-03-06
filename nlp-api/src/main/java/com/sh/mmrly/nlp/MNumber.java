package com.sh.mmrly.nlp;

import org.jetbrains.annotations.NotNull;

public record MNumber(@NotNull String ref) implements Referencable {
  public static final MNumber SINGULAR = new MNumber("Sing");
  public static final MNumber PLURAL = new MNumber("Plur");

  public static MNumber valueOf(String value) {
    return value == null ? null : new MNumber(value);
  }

  @Override
  public String toString() {
    return ref;
  }
}
