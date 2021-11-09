package com.sh.mmrly;

public record SelectionType(String ref) {
  public static final SelectionType ERROR = new SelectionType("ERR");

  public static SelectionType valueOf(String value) {
    return value == null ? null : new SelectionType(value);
  }
}
