package com.sh.mmrly.rules;

import com.sh.mmrly.SentenceSplitter;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Naive implementation of sentence splitter
 */
@ApplicationScoped
public class SentenceSplitterImpl implements SentenceSplitter {
  @Override
  public List<String> split(String text) {
    int s = 0;
    final List<String> res = new ArrayList<>();
    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i);
      if (ch == '.' || ch == '!' || ch == '?') {
        res.add(text.substring(s, i + 1).trim());
        s = i + 1;
      }
    }
    if (s != text.length()) {
      res.add(text.substring(s).trim());
    }
    return res;
  }
}
