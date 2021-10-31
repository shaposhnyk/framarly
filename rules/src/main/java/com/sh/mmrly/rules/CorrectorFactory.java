package com.sh.mmrly.rules;

import com.sh.mmrly.Corrector;
import com.sh.mmrly.MammarlyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class CorrectorFactory {
  private static final Logger logger = LoggerFactory.getLogger(CorrectorFactory.class);

  private final MammarlyConfig config;

  @Inject
  public CorrectorFactory(MammarlyConfig config) {
    this.config = config;
  }

  @Produces
  @Default
  public Corrector corrector() {
    logger.info("Instantiating corrector ... ");
    if (config.mock()) {
      logger.info("Instantiating corrector type1");
      return new CorrectorImpl();
    } else {
      logger.info("Instantiating corrector type2");
      return new Corrector2Impl();
    }
  }
}
