package com.sh.mmrly.nlp;

public record DEP(String ref) {
  public static final DEP ROOT = new DEP("ROOT");
  public static final DEP nsubj = new DEP("nsubj");
  public static final DEP tense = new DEP("aux:tense");
  public static final DEP OTHER = new DEP("other");
}
