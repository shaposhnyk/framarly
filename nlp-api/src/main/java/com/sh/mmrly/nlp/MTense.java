package com.sh.mmrly.nlp;

import org.jetbrains.annotations.NotNull;

public record MTense(@NotNull String ref) implements Referencable {
  public static MTense valueOf(String value) {
    return value == null ? null : new MTense(value);
  }
}
