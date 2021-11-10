package com.sh.mmrly.nlp;

import org.jetbrains.annotations.NotNull;

public record MGender(@NotNull String ref) implements Referencable {
  public static final MGender MASC = new MGender("Masc");
  public static final MGender FEM = new MGender("Fem");

  public static MGender valueOf(String value) {
    return value == null ? null : new MGender(value);
  }

  @Override
  public String toString() {
    return ref;
  }
}
