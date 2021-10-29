package com.sh.mmrly.nlp;

import org.jetbrains.annotations.Nullable;

public interface Morph {
  @Nullable
  MNumber number();

  @Nullable
  default MGender gender() {
    return null;
  }

  @Nullable
  default MDefinite definite() {
    return null;
  }

  @Nullable
  default MPerson person() {
    return null;
  }

  @Nullable
  default MTense tense() {
    return null;
  }
}
