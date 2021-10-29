package com.sh.mmrly.nlp;

public record MPerson(String ref) {
  public static final MPerson FIRST = new MPerson("1");
  public static final MPerson SECOND = new MPerson("2");
  public static final MPerson THIRD = new MPerson("3");
}
