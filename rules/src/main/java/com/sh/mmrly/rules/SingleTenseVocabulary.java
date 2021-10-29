package com.sh.mmrly.rules;

import com.sh.mmrly.Vocabulary;
import com.sh.mmrly.nlp.Morph;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingleTenseVocabulary implements Vocabulary {
  private final Map<String, Vocabulary> wordMap;

  SingleTenseVocabulary(Map<String, Vocabulary> wordMap) {
    this.wordMap = wordMap;
  }

  public static SingleTenseVocabulary createConcurrent() {
    return new SingleTenseVocabulary(new ConcurrentHashMap<>());
  }

  public static SingleTenseVocabulary create() {
    return new SingleTenseVocabulary(new LinkedHashMap<>());
  }

  @Override
  public String form(@NotNull String lemma, @NotNull Morph morph) {
    Vocabulary word = wordMap.get(lemma);
    return word == null ? null : word.form(lemma, morph);
  }

  public Vocabulary add(String lemma, Vocabulary wordVocab) {
    return wordMap.put(lemma, wordVocab);
  }

  @Override
  public String toString() {
    return "SingleTenseVocabulary{" +
        "wordMap=" + wordMap +
        '}';
  }
}
