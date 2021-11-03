package com.sh.mmrly.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.sh.mmrly.RuleCode;
import io.quarkus.jackson.ObjectMapperCustomizer;
import io.quarkus.logging.Log;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.configuration.ProfileManager;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;

@ApplicationScoped
public class WsObjectMapperCustomizer implements ObjectMapperCustomizer {
  public void customize(ObjectMapper mapper) {
    if (ProfileManager.getLaunchMode().equals(LaunchMode.DEVELOPMENT)) {
      Log.info("Activating JSON pretty printing");
      mapper.enable(SerializationFeature.INDENT_OUTPUT);

      mapper.registerModule(new CustomModule());
    }
  }

  private static class CustomModule extends Module {
    @Override
    public String getModuleName() {
      return "rules-adapters";
    }

    @Override
    public Version version() {
      return Version.unknownVersion();
    }

    @Override
    public void setupModule(Module.SetupContext context) {
      SimpleDeserializers sd = new SimpleDeserializers();
      context.addDeserializers(sd);

      SimpleSerializers ss = new SimpleSerializers();
      ss.addSerializer(RuleCode.class, new JsonSerializer<>() {
        @Override
        public void serialize(RuleCode value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
          gen.writeString(value == null ? null : value.ref());
        }
      });
      context.addSerializers(ss);
    }
  }
}