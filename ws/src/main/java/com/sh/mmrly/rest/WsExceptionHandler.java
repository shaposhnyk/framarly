package com.sh.mmrly.rest;

import com.sh.mmrly.TWS;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WsExceptionHandler implements ExceptionMapper<IllegalArgumentException> {
  @Override
  public Response toResponse(IllegalArgumentException exception) {
    return Response.status(Response.Status.BAD_REQUEST).entity(TWS.textOf(exception.getMessage())).build();
  }
}