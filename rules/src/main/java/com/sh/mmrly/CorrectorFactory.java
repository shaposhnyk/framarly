package com.sh.mmrly;

import com.sh.mmrly.nlp.Tokenizer;
import com.sh.mmrly.rules.DummyCorrector;
import com.sh.mmrly.rules.SpacyCorrector;
import io.quarkus.arc.DefaultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class CorrectorFactory {
  private static final Logger logger = LoggerFactory.getLogger(CorrectorFactory.class);

  private final MammarlyConfig config;
  private final SpacyCorrector spacyCorrector;
  private final DummyCorrector dummyCorrector;

  @Inject
  public CorrectorFactory(
      MammarlyConfig config,
      Tokenizer tokenizer,
      Instance<RuleChecker> checkers
  ) {
    logger.info("Instantiating corrector ... ");
    this.config = config;
    this.spacyCorrector = new SpacyCorrector(tokenizer, checkers);
    this.dummyCorrector = new DummyCorrector();
  }

  @Produces
  @DefaultBean
  public Corrector corrector() {
    if (config.mock()) {
      logger.info("Returning dummy corrector");
      return dummyCorrector;
    } else {
      logger.debug("Returning spacy corrector");
      return spacyCorrector;
    }
  }
}
