package com.sh.mmrly;

import com.sh.mmrly.nlp.Referencable;

public record RuleCode(String ref) implements Referencable {
  public static RuleCode valueOf(String value) {
    return value == null ? null : new RuleCode(value);
  }
}
