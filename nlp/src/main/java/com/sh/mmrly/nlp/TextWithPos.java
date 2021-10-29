package com.sh.mmrly.nlp;

import java.util.List;

public record TextWithPos(
    String text,
    List<TaggedToken> tags
) {
}
