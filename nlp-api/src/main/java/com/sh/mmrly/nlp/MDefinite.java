package com.sh.mmrly.nlp;

import org.jetbrains.annotations.NotNull;

public record MDefinite(@NotNull String ref) {
  public static final MDefinite DEF = new MDefinite("Def");
  public static final MDefinite IND = new MDefinite("Ind");

  public static MDefinite valueOf(String value) {
    return value == null ? null : new MDefinite(value);
  }
}
