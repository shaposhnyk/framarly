package com.sh.mmrly.nlp;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Parsed sentence containing raw sentence string and list of POS tokens
 */
public record ParsedSentence(
    @NotNull String text,
    @NotNull List<TaggedToken> tags
) {
}
