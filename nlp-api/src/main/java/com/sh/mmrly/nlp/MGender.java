package com.sh.mmrly.nlp;

import org.jetbrains.annotations.NotNull;

public record MGender(@NotNull String ref) {
  public static final MGender MASC = new MGender("Masc");
  public static final MGender FEM = new MGender("FEM");

  public static MGender valueOf(String value) {
    return value == null ? null : new MGender(value);
  }
}
