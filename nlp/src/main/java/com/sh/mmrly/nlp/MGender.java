package com.sh.mmrly.nlp;

public record MGender(String ref) {
  public static final MGender MASC = new MGender("Masc");
  public static final MGender FEM = new MGender("FEM");
}
