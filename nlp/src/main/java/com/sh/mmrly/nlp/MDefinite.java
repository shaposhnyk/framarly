package com.sh.mmrly.nlp;

public record MDefinite(String ref) {
  public static final MDefinite DEF = new MDefinite("Def");
  public static final MDefinite IND = new MDefinite("Ind");
}
