package com.sh.rules;

import com.sh.nlp.Morph;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingleTenseVocabulary implements VerbVocabulary {
  private final Map<String, VerbVocabulary> wordMap;

  SingleTenseVocabulary(Map<String, VerbVocabulary> wordMap) {
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
    VerbVocabulary word = wordMap.get(lemma);
    return word == null ? null : word.form(lemma, morph);
  }

  public VerbVocabulary add(String lemma, VerbVocabulary wordVocab) {
    return wordMap.put(lemma, wordVocab);
  }

  @Override
  public String toString() {
    return "SingleTenseVocabulary{" +
        "wordMap=" + wordMap +
        '}';
  }
}
