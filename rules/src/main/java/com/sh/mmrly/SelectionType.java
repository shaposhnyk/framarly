package com.sh.mmrly;

import com.sh.mmrly.nlp.Referencable;

public record SelectionType(String ref) implements Referencable {
  public static final SelectionType ERROR = new SelectionType("ERR");

  public static SelectionType valueOf(String value) {
    return value == null ? null : new SelectionType(value);
  }
}
