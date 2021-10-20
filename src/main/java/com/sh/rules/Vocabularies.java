package com.sh.rules;

public class Vocabularies {

  public static VerbVocabulary presentMini() {
    SingleTenseVocabulary v = SingleTenseVocabulary.create();
    v.add("avoir", SingleWordVocabulary.create("ai", "as", "a", "avons", "avez", "ont"));
    v.add("être", SingleWordVocabulary.create("suis", "es", "est", "sommes", "êtes", "sont"));
    v.add("aller", SingleWordVocabulary.create("vais", "vas", "va", "allons", "allez", "vont"));
    v.add("faire", SingleWordVocabulary.create("fais", "fais", "fait", "faisons", "faitez", "font"));
    return v;
  }

}
