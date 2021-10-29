package com.sh.mmrly.nlp;

public record MNumber(String ref) {
  public static final MNumber SINGULAR = new MNumber("Sing");
  public static final MNumber PLURAL = new MNumber("Plur");
}
