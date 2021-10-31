package com.sh.mmrly.nlp.spacy;

import com.sh.mmrly.nlp.*;
import org.jetbrains.annotations.Nullable;

public record SpacyMorph(
    MDefinite Definite,
    MGender Gender,
    MNumber Number,
    MTense Tense,
    MPerson Person
) implements Morph {

  @Override
  public @Nullable MNumber number() {
    return Number;
  }

  @Override
  public @Nullable MGender gender() {
    return Gender;
  }

  @Override
  public @Nullable MDefinite definite() {
    return Definite;
  }

  @Override
  public @Nullable MPerson person() {
    return Person;
  }

  @Override
  public @Nullable MTense tense() {
    return Tense;
  }
}
