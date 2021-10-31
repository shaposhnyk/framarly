package com.sh.mmrly.rest;

import com.sh.mmrly.MammarlyConfig;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * Quarkus application, initializing missing dependencies
 */
@ApplicationScoped
public class QApplication {
  private static final Logger logger = LoggerFactory.getLogger(QApplication.class);

  @Produces
  public MammarlyConfig config() {
    logger.info("config: {}", ConfigProvider.getConfig().getValue("mamarly.mock", String.class));
    return () -> ConfigProvider.getConfig().getValue("mamarly.mock", Boolean.class);
  }
}
