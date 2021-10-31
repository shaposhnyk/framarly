package com.sh.mmrly.rules;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SentenceSplitterImplTest {
  final SentenceSplitterImpl splitter = new SentenceSplitterImpl();

  @Test
  void splitSentences() {
    assertThat(split("Do something! Should I? No.")).containsExactly("Do something!", "Should I?", "No.");
    assertThat(split("Do something! Should I? No")).containsExactly("Do something!", "Should I?", "No");
    assertThat(split("Do something! Should I? No ")).containsExactly("Do something!", "Should I?", "No");
    assertThat(split("Do something, should I; No")).containsExactly("Do something, should I; No");
  }

  private List<String> split(String text) {
    return splitter.split(text);
  }
}