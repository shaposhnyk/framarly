package com.sh.rules;

import com.sh.nlp.*;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PronounCheckerTest extends AbstractCheckerTest {
  final PronounChecker emptyChk = new PronounChecker((a, b) -> null);
  final PronounChecker miniChk = new PronounChecker(Vocabularies.presentMini());

  final TaggedToken JE = new TaggedToken("je", "je", POS.PRON, DEP.nsubj, Morphs.of(MPerson.FIRST, MNumber.SINGULAR), List.of(), " ");

  final TaggedToken FAIS = new TaggedToken("fais", "faire", POS.VERB, DEP.OTHER, Morphs.of(MPerson.FIRST, MNumber.SINGULAR), List.of(0, 1), " ");
  final TaggedToken FAIT = new TaggedToken("fait", "faire", POS.VERB, DEP.OTHER, Morphs.of(MPerson.THIRD, MNumber.SINGULAR), List.of(0, 1), " ");

  final TaggedToken A = new TaggedToken("a", "avoir", POS.VERB, DEP.tense, Morphs.of(MPerson.THIRD, MNumber.SINGULAR), List.of(), " ");
  final TaggedToken AI = new TaggedToken("ai", "avoir", POS.VERB, DEP.tense, Morphs.of(MPerson.FIRST, MNumber.SINGULAR), List.of(), " ");

  @Test
  public void correctPhrase() {
    List<TaggedToken> sentence = List.of(t("Hier"), JE, FAIS, t("ca"));
    assertThat(emptyChk.makeSuggestions(sentence)).isEmpty();
  }

  @Test
  public void correctPhraseAux() {
    List<TaggedToken> sentence = List.of(t("Hier"), JE, AI, FAIS.withChildren(1, 2), t("ca"));
    assertThat(emptyChk.makeSuggestions(sentence)).isEmpty();
  }

  @Test
  public void incorrectPhraseNoVocab() {
    List<TaggedToken> sentence = List.of(t("Hier"), JE, FAIT, t("ca"));
    assertSelection(emptyChk, sentence).containsExactly(1, 2);
    assertReplacementTxt(emptyChk, sentence).isEmpty();
  }

  @Test
  public void incorrectPhraseVocab() {
    List<TaggedToken> sentence = List.of(t("Hier"), JE, FAIT, t("ca"));
    assertSelection(miniChk, sentence).containsExactly(1, 2);
    assertReplacement(miniChk, sentence).containsExactly(Replacement.of(2, "fais"));
  }

  @Test
  public void incorrectPhraseAuxNoVocab() {
    List<TaggedToken> sentence = List.of(t("Hier"), JE, A, FAIT.withChildren(0, 1, 2), t("ca"));
    assertSelection(emptyChk, sentence).containsExactly(1, 2, 3);
  }

  @Test
  public void incorrectPhraseAuxVocab() {
    List<TaggedToken> sentence = List.of(t("Hier"), JE, A, FAIT.withChildren(0, 1, 2), t("ca"));
    assertSelection(miniChk, sentence).containsExactly(1, 2, 3);
    assertReplacement(miniChk, sentence).containsExactly(Replacement.of(2, "ai"));
  }
}