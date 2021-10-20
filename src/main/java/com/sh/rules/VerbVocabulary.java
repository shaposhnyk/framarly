package com.sh.rules;

import com.sh.nlp.Morph;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface VerbVocabulary {
  /**
   * @return a particular verb form for a given morphology. null if not known
   */
  @Nullable
  String form(@NotNull String lemma, @NotNull Morph morph);
}
