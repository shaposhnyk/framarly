package com.sh.mmrly.nlp;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record TextWithPos(
    @NotNull String text,
    @NotNull List<TaggedToken> tags
) {
}
