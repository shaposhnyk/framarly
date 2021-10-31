package com.sh.mmrly.nlp.spacy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.function.Function;

final class ReferencableDeserializer<T> extends JsonDeserializer<T> {
  private final Function<String, T> fx;

  ReferencableDeserializer(Function<String, T> fx) {
    this.fx = fx;
  }

  @Override
  public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return fx.apply(p.getValueAsString());
  }
}
