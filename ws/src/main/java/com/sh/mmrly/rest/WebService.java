package com.sh.mmrly.rest;

import com.sh.mmrly.Corrector;
import com.sh.mmrly.Suggestion;
import com.sh.mmrly.TextWithSuggestions;
import com.sh.mmrly.nlp.TextWithWhitespace;
import io.quarkus.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class WebService {
  private static final Logger logger = LoggerFactory.getLogger(WebService.class);
  private final Corrector corrector;

  @Inject
  public WebService(Corrector corrector) {
    this.corrector = corrector;
  }

  @GET
  @Path("/todos")
  @Produces(MediaType.APPLICATION_JSON)
  public TextWithSuggestions suggestChanges(@QueryParam("text") String text) {
    Log.infov("Suggesting changes for: {0}", text);
    return corrector.makeSuggestions(text);
  }

  @POST
  @Path("/todos")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public TextWithSuggestions apply(TextWithSuggestions input) {
    List<TextWithWhitespace> sentence = input.sentence();
    if (input.suggestions().size() < 1) {
      throw new IllegalArgumentException();
    }
    TextWithSuggestions result = null;
    for (Suggestion suggestion : input.suggestions()) {
      result = corrector.applySuggestion(sentence, suggestion);
      sentence = result.sentence();
    }
    return result;
  }
}