package com.sh.mmrly.nlp.spacy;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;

@RegisterRestClient(configKey = "spacy-api", baseUri = "http://localhost:8081")
public interface SpacyRestService {
  @GET
  @Path("/pos")
  @Produces("application/json")
  SpacyResponse tokenize(@QueryParam("text") String text);

  @POST
  @Path("/pos")
  @Produces("application/json")
  SpacyResponse tokenizePrivate(String text);
}
