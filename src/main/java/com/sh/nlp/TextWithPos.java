package com.sh.nlp;

import java.util.List;

public record TextWithPos(
    String text,
    List<TaggedToken> tags
) {
}
