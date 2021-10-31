package com.sh.mmrly.nlp.spacy;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class SpacyRestServiceIT {
  @Test
  public void test() {
    given()
        .when().get("/legumes")
        .then()
        .statusCode(200);
  }
}