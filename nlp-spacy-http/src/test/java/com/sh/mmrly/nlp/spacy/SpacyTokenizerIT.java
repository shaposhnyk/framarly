package com.sh.mmrly.nlp.spacy;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class SpacyTokenizerIT {

  @Inject
  SpacyTokenizer srv;


  @Test
  void splitSentences() {
    var res = srv.tokenize("Le vin coule.").data();
    assertThat(res).isNotNull();
  }

}