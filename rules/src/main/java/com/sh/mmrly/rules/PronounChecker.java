package com.sh.mmrly.rules;

import com.sh.mmrly.*;
import com.sh.mmrly.nlp.DEP;
import com.sh.mmrly.nlp.POS;
import com.sh.mmrly.nlp.TaggedToken;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PronounChecker implements RuleChecker {
  private final Vocabulary vocab;

  public PronounChecker(Vocabulary vocab) {
    this.vocab = vocab;
  }

  @Override
  public RuleCode code() {
    return new RuleCode("PRONOUN_ACC");
  }

  @Override
  public List<Suggestion> makeSuggestions(List<TaggedToken> sentence) {
    final List<Suggestion> suggestions = new ArrayList<>();
    int i = 0;
    for (TaggedToken verb : sentence) {
      if (POS.VERB.equals(verb.pos())) {
        for (int j : verb.children()) {
          TaggedToken pronoun = sentence.get(j);
          if (POS.PRON.equals(pronoun.pos())
              && DEP.nsubj.equals(pronoun.dep())
              && pronoun.morph().person() != null
          ) {

            final var k = verbOrAuxIdx(verb, sentence);
            final var mVerb = (k < 0) ? verb : sentence.get(k);
            final var vm = mVerb.morph();
            final var pnm = pronoun.morph();
            if (!pnm.person().equals(vm.person()) || !pnm.number().equals(vm.number())) {
              Suggestion s = suggestVerbChange(mVerb, pronoun, i, j, k);
              suggestions.add(s);
            }

          }
        }
      }
      i++;
    }
    return suggestions;
  }

  private Suggestion suggestVerbChange(TaggedToken mVerb, TaggedToken pronoun, int i, int j, int k) {
    final String form = vocab.form(mVerb.lemma(), pronoun.morph());
    if (form == null) {
      return k < 0 ? Suggestion.of(title(), i, j) : Suggestion.of(title(), i, j, k);
    }
    final var formws = TWS.of(form, mVerb.whitespace());
    final var change = k < 0 ? Replacement.replaceAt(i, formws) : Replacement.replaceAt(k, formws);
    return k < 0 ? Suggestion.changeOf(title(), change, i, j) : Suggestion.changeOf(title(), change, i, j, k);
  }

  private String title() {
    return "Pronoun should accord with its verb";
  }

  private int verbOrAuxIdx(TaggedToken verb, List<TaggedToken> sentence) {
    for (int idx : verb.children()) {
      final var aux = sentence.get(idx);
      if (DEP.tense.equals(aux.dep())) {
        return idx;
      }
    }
    return -1;
  }
}
