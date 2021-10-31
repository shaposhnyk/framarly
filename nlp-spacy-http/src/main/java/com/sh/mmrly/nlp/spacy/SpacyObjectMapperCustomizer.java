package com.sh.mmrly.nlp.spacy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.sh.mmrly.nlp.*;
import io.quarkus.jackson.ObjectMapperCustomizer;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.function.Function;

@ApplicationScoped
public class SpacyObjectMapperCustomizer implements ObjectMapperCustomizer {

  public final CustomModule NLP_MODULE = new CustomModule();

  static <T> ReferencableDeserializer<T> referencableOf(Function<String, T> fx) {
    return new ReferencableDeserializer<>(fx);
  }

  public void customize(ObjectMapper mapper) {
    mapper.registerModule(nlpModule());
  }

  public Module nlpModule() {
    return NLP_MODULE;
  }

  private static class CustomModule extends Module {
    @Override
    public String getModuleName() {
      return "spacy-nlp-adapters";
    }

    @Override
    public Version version() {
      return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext context) {
      SimpleDeserializers d = new SimpleDeserializers();
      d.addDeserializer(DEP.class, referencableOf(DEP::valueOf));
      d.addDeserializer(POS.class, referencableOf(POS::valueOf));
      d.addDeserializer(MDefinite.class, referencableOf(MDefinite::valueOf));
      d.addDeserializer(MGender.class, referencableOf(MGender::valueOf));
      d.addDeserializer(MPerson.class, referencableOf(MPerson::valueOf));
      d.addDeserializer(MTense.class, referencableOf(MTense::valueOf));
      d.addDeserializer(MNumber.class, referencableOf(MNumber::valueOf));

      SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
      resolver.addMapping(Morph.class, SpacyMorph.class);

      context.addAbstractTypeResolver(resolver);
      context.addDeserializers(d);
    }
  }

  static final class ReferencableDeserializer<T> extends JsonDeserializer<T> {
    private final Function<String, T> fx;

    ReferencableDeserializer(Function<String, T> fx) {
      this.fx = fx;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
      return fx.apply(p.getValueAsString());
    }
  }
}