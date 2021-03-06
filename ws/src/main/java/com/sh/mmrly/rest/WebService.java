package com.sh.mmrly.rest;

import com.sh.mmrly.Corrector;
import com.sh.mmrly.Suggestion;
import com.sh.mmrly.TextWithMarkup;
import com.sh.mmrly.TextWithSuggestions;
import io.quarkus.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.time.Instant;
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
    Instant starts = Instant.now();
    TextWithSuggestions result = corrector.makeSuggestions(text);
    Log.infov("Suggested {0} changes for: {1} in {2} ms", result.suggestions().size(), text, Duration.between(starts, Instant.now()).multipliedBy(1000).getSeconds());
    return result;
  }

  @POST
  @Path("/todos")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public TextWithSuggestions apply(TextWithSuggestions input) {
    List<TextWithMarkup> sentence = input.sentence();
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