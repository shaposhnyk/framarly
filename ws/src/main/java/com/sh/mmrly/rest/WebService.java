package com.sh.mmrly.rest;

import com.sh.mmrly.Corrector;
import com.sh.mmrly.rules.TextWithSuggestions;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class WebService {
  @Inject
  private Corrector corrector;

  @GET
  @Path("/todos")
  @Produces(MediaType.APPLICATION_JSON)
  public TextWithSuggestions suggestChanges(@QueryParam("text") String text) {
    return corrector.makeSuggestions(text);
  }
}