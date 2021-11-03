package com.sh.mmrly.rules;

import com.sh.mmrly.Replacement;
import com.sh.mmrly.RuleChecker;
import com.sh.mmrly.RuleCode;
import com.sh.mmrly.Suggestion;
import com.sh.mmrly.nlp.*;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DeterminerChecker implements RuleChecker {
  private static final Map<MDefinite, String> numberMap = Map.of(
      MDefinite.DEF, "les",
      MDefinite.IND, "des"
  );
  private static final Map<MDefinite, Map<MGender, String>> genderMap = Map.of(
      MDefinite.DEF, Map.of(MGender.MASC, "le", MGender.FEM, "la"),
      MDefinite.IND, Map.of(MGender.MASC, "un", MGender.FEM, "une")
  );

  @Override
  public RuleCode code() {
    return new RuleCode("DET_ACC");
  }

  @Override
  public List<Suggestion> makeSuggestions(List<TaggedToken> sentence) {
    final List<Suggestion> suggestions = new ArrayList<>();
    int i = 0;
    for (TaggedToken noun : sentence) {
      if (POS.NOUN.equals(noun.pos())) {
        for (int j : noun.children()) {
          TaggedToken det = sentence.get(j);
          if (POS.DET.equals(det.pos()) && det.morph().definite() != null) {

            final Morph nm = noun.morph();
            if (MNumber.PLURAL.equals(nm.number()) && !nm.number().equals(det.morph().number())) {
              String replacement = numberMap.get(det.morph().definite());
              suggestions.add(determinerChangeOf(i, j, replacement));
            } else if (MNumber.SINGULAR.equals(nm.number())
                && nm.gender() != null && !nm.gender().equals(det.morph().gender())) {
              String replacement = genderMap.get(det.morph().definite()).get(nm.gender());
              suggestions.add(determinerChangeOf(i, j, replacement));
            }

          }
        }
      }
      i++;
    }
    return suggestions;
  }

  private Suggestion determinerChangeOf(int nounIdx, int detIdx, String replacement) {
    Replacement detChg = Replacement.replaceAt(detIdx, replacement);
    return Suggestion.changeOf(code(), detChg, detIdx, nounIdx);
  }
}
