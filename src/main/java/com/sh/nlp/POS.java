package com.sh.nlp;

public record POS(String value) {
  public static final POS UNKNOWN = new POS("UNKNOWN");
  public static final POS DET = new POS("DET");
  public static final POS NOUN = new POS("NOUN");
  public static final POS PRON = new POS("PRON");
  public static final POS VERB = new POS("VERB");
  public static final POS PUNCT = new POS("PUNCT");
}
