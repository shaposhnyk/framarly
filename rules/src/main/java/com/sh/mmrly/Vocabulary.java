package com.sh.mmrly;

import com.sh.mmrly.nlp.Morph;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Vocabulary {
  /**
   * @return a particular form for a given morphology. null if not known
   */
  @Nullable
  String form(@NotNull String lemma, @NotNull Morph morph);
}
