package com.sh.mmrly.nlp.spacy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.mmrly.nlp.DEP;
import com.sh.mmrly.nlp.POS;
import com.sh.mmrly.nlp.TaggedToken;
import com.sh.mmrly.nlp.TextWithPos;
import io.quarkus.jackson.runtime.JacksonBuildTimeConfig;
import io.quarkus.jackson.runtime.ObjectMapperProducer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SpacyObjectMapperCustomizerTest {

  static final ObjectMapper om = new ObjectMapperProducer()
      .objectMapper(SingleInstance.of(new SpacyObjectMapperCustomizer()), jacksonBuildTimeConfig());

  private static JacksonBuildTimeConfig jacksonBuildTimeConfig() {
    JacksonBuildTimeConfig config = new JacksonBuildTimeConfig();
    config.timezone = Optional.empty();
    config.serializationInclusion = Optional.empty();
    return config;
  }

  @Test
  public void testDeserialization() throws IOException {
    InputStream in = Objects.requireNonNull(getClass().getResourceAsStream("/spacy-pos.json"));
    SpacyResponse json = om.createParser(in)
        .readValueAs(SpacyResponse.class);

    assertThat(json.data()).extracting(TextWithPos::text)
        .containsExactly("Le vin coule.", "Coulé.");
    TaggedToken token = json.data().get(1).tags().get(0);
    assertThat(token.pos()).isEqualTo(POS.valueOf("myPos"));
    assertThat(token.dep()).isEqualTo(DEP.valueOf("myDep"));
    assertThat(token.children()).containsExactly(0);
    assertThat(token.whitespace()).isEqualTo(".");
    assertThat(token.lemma()).isEqualTo("Coule");
    assertThat(token.morph()).isNotNull();
  }

}