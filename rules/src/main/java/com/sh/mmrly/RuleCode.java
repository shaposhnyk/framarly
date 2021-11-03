package com.sh.mmrly;

public record RuleCode(String ref) {
  public static RuleCode valueOf(String value) {
    return value == null ? null : new RuleCode(value);
  }
}
