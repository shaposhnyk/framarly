package com.sh.mmrly.nlp.spacy;

import com.sh.mmrly.nlp.ParsedSentence;
import com.sh.mmrly.nlp.TaggedToken;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static com.sh.mmrly.nlp.POS.*;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class SpacyTokenizerIT {

  @Inject
  SpacyTokenizer srv;


  @Test
  void splitSentences() {
    var res = srv.tokenize("Le vin coule.").data();
    Log.infov("Result is {0}", res);
    assertThat(res).isNotNull()
        .flatExtracting(ParsedSentence::tags)
        .extracting(TaggedToken::pos)
        .containsExactly(DET, NOUN, VERB, PUNCT);
  }

}