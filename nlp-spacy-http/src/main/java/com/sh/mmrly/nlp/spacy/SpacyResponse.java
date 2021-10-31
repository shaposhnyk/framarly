package com.sh.mmrly.nlp.spacy;

import com.sh.mmrly.nlp.ParsedSentence;

import java.util.List;

public record SpacyResponse(List<ParsedSentence> data) {
}
