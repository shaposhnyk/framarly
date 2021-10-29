package com.sh.mmrly.rules;

import com.sh.mmrly.Vocabulary;
import com.sh.mmrly.nlp.MNumber;
import com.sh.mmrly.nlp.MPerson;
import com.sh.mmrly.nlp.Morph;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class SingleWordVocabulary implements Vocabulary {
  private final Map<MPerson, Map<MNumber, String>> map;

  public SingleWordVocabulary(Map<MPerson, Map<MNumber, String>> map) {
    this.map = Map.copyOf(map);
  }

  public static Vocabulary create(String je, String tu, String il, String nous, String vous, String ils) {
    final var wordMap = Map.of(
        MPerson.FIRST, Map.of(MNumber.SINGULAR, je, MNumber.PLURAL, nous),
        MPerson.SECOND, Map.of(MNumber.SINGULAR, tu, MNumber.PLURAL, vous),
        MPerson.THIRD, Map.of(MNumber.SINGULAR, il, MNumber.PLURAL, ils)
    );
    return new SingleWordVocabulary(wordMap);
  }

  @Override
  public String form(@NotNull String lemma, @NotNull Morph morph) {
    return morph.person() == null || morph.number() == null ? null : map.get(morph.person()).get(morph.number());
  }

  @Override
  public String toString() {
    return "SingleWordVocabulary{" +
        "map=" + map +
        '}';
  }
}
