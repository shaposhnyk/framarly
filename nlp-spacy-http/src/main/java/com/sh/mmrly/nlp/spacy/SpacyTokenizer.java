package com.sh.mmrly.nlp.spacy;

import com.sh.mmrly.nlp.ParsedSentence;
import com.sh.mmrly.nlp.Tokenizer;
import com.sh.mmrly.nlp.XResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Call spacy rest service to tokenize sentence
 */
@ApplicationScoped
public class SpacyTokenizer implements Tokenizer {
  private final SpacyRestService spacy;

  @Inject
  public SpacyTokenizer(@RestClient SpacyRestService spacy) {
    this.spacy = spacy;
  }

  @Override
  public XResponse<List<ParsedSentence>> tokenize(String text) {
    var data = spacy.tokenize(text).data();
    return new XResponse<>(data);
  }
}
