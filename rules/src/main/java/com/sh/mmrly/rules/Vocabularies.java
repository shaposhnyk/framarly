package com.sh.mmrly.rules;

import com.sh.mmrly.Vocabulary;

public class Vocabularies {

  public static Vocabulary presentMini() {
    SingleTenseVocabulary v = SingleTenseVocabulary.create();
    v.add("avoir", SingleWordVocabulary.create("ai", "as", "a", "avons", "avez", "ont"));
    v.add("être", SingleWordVocabulary.create("suis", "es", "est", "sommes", "êtes", "sont"));
    v.add("aller", SingleWordVocabulary.create("vais", "vas", "va", "allons", "allez", "vont"));
    v.add("faire", SingleWordVocabulary.create("fais", "fais", "fait", "faisons", "faitez", "font"));
    v.add("pouvoir", SingleWordVocabulary.create("peux", "peux", "peut", "pouvons", "pouvez", "peuvent"));
    return v;
  }

}
