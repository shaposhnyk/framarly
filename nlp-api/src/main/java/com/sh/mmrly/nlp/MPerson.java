package com.sh.mmrly.nlp;

import org.jetbrains.annotations.NotNull;

public record MPerson(@NotNull String ref) {
  public static final MPerson FIRST = new MPerson("1");
  public static final MPerson SECOND = new MPerson("2");
  public static final MPerson THIRD = new MPerson("3");

  public static MPerson valueOf(String value) {
    return value == null ? null : new MPerson(value);
  }
}
