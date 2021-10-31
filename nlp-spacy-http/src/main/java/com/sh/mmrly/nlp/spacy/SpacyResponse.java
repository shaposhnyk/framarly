package com.sh.mmrly.nlp.spacy;

import com.sh.mmrly.nlp.TextWithPos;

import java.util.List;

public record SpacyResponse(List<TextWithPos> data) {
}
