package com.sh.nlp;

import java.util.Objects;

public class Morphs {
  public static final Morph EMPTY = new MGN(null, null);
  public static final Morph SINGULAR = new MGN(null, MNumber.SINGULAR);
  public static final Morph PLURAL = new MGN(null, MNumber.PLURAL);

  public static Morph of(MGender gender, MNumber number) {
    return new MGN(Objects.requireNonNull(gender), Objects.requireNonNull(number));
  }

  public static Morph of(MPerson person, MNumber number) {
    return new MPN(person, number);
  }

  public static Morph determinerOf(MGender gender, MNumber number, MDefinite def) {
    return new MGND(gender, number, def);
  }

  record MGN(MGender gender, MNumber number) implements Morph {
  }

  record MPN(MPerson person, MNumber number) implements Morph {
  }

  record MGND(MGender gender, MNumber number, MDefinite definite) implements Morph {
  }
}
