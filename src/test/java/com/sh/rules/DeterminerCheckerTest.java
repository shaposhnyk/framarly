package com.sh.rules;

import com.sh.nlp.*;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeterminerCheckerTest extends AbstractCheckerTest {

  final DeterminerChecker checker = new DeterminerChecker();

  final TaggedToken LE = TaggedToken.posOf("Le", POS.DET, Morphs.determinerOf(MGender.MASC, MNumber.SINGULAR, MDefinite.DEF));
  final TaggedToken LA = TaggedToken.posOf("La", POS.DET, Morphs.determinerOf(MGender.FEM, MNumber.SINGULAR, MDefinite.DEF));
  final TaggedToken UN = TaggedToken.posOf("Un", POS.DET, Morphs.determinerOf(MGender.MASC, MNumber.SINGULAR, MDefinite.IND));
  final TaggedToken UNE = TaggedToken.posOf("Une", POS.DET, Morphs.determinerOf(MGender.FEM, MNumber.SINGULAR, MDefinite.IND));
  final TaggedToken LES = TaggedToken.posOf("Les", POS.DET, Morphs.determinerOf(null, MNumber.PLURAL, MDefinite.DEF));
  final TaggedToken VIN = new TaggedToken("vin", "vin", POS.NOUN, DEP.OTHER, Morphs.of(MGender.MASC, MNumber.SINGULAR), List.of(0, 2, 3), " ");
  final TaggedToken VINS = new TaggedToken("vins", "vin", POS.NOUN, DEP.OTHER, Morphs.of(MGender.MASC, MNumber.PLURAL), List.of(0, 2, 3), " ");
  final TaggedToken PIZZA = new TaggedToken("pizza", "pizza", POS.NOUN, DEP.OTHER, Morphs.of(MGender.FEM, MNumber.SINGULAR), List.of(0, 2, 3), " ");

  @Test
  public void correctPhrase() {
    List<TaggedToken> sentence = List.of(LE, VIN, t("est"), t("bon"));
    assertThat(checker.makeSuggestions(sentence)).isEmpty();
  }

  @Test
  public void incorectGenderDetPlural() {
    List<TaggedToken> sentence = List.of(LA, VINS, t("sont"), t("bons"));
    assertReplacementTxt(checker, sentence)
        .containsExactly("les");
    assertSelection(checker, sentence)
        .containsExactly(0, 1);
  }

  @Test
  public void incorectGenderIndefPlural() {
    List<TaggedToken> sentence = List.of(UNE, VINS, t("sont"), t("bons"));
    assertReplacementTxt(checker, sentence)
        .containsExactly("des");
    assertSelection(checker, sentence)
        .containsExactly(0, 1);
  }

  @Test
  public void incorectGenderDet() {
    List<TaggedToken> sentence = List.of(LA, VIN, t("est"), t("bon"));
    assertReplacementTxt(checker, sentence)
        .containsExactly("le");
    assertSelection(checker, sentence)
        .containsExactly(0, 1);
  }

  @Test
  public void incorectGenderDet2() {
    List<TaggedToken> sentence = List.of(LE, PIZZA, t("est"), t("bonne"));
    assertReplacementTxt(checker, sentence)
        .containsExactly("la");
    assertSelection(checker, sentence)
        .containsExactly(0, 1);
  }

  @Test
  public void incorectGenderDet3() {
    List<TaggedToken> sentence = List.of(LES, VIN, t("est"), t("bon"));
    assertReplacementTxt(checker, sentence)
        .containsExactly("le");
    assertSelection(checker, sentence)
        .containsExactly(0, 1);
  }

  @Test
  public void incorectGenderDet4() {
    List<TaggedToken> sentence = List.of(LES, PIZZA, t("est"), t("bonne"));
    assertReplacementTxt(checker, sentence)
        .containsExactly("la");
    assertSelection(checker, sentence)
        .containsExactly(0, 1);
  }

  @Test
  public void incorectGenderIndef() {
    List<TaggedToken> sentence = List.of(UNE, VIN, t("est"), t("bon"));
    assertReplacementTxt(checker, sentence)
        .containsExactly("un");
    assertSelection(checker, sentence)
        .containsExactly(0, 1);
  }

  @Test
  public void incorectGenderIndef2() {
    List<TaggedToken> sentence = List.of(UN, PIZZA, t("est"), t("bonne"));
    assertReplacementTxt(checker, sentence)
        .containsExactly("une");
    assertSelection(checker, sentence)
        .containsExactly(0, 1);
  }

}