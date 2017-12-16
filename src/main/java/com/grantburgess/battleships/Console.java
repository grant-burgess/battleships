package com.grantburgess.battleships;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Console {
  List<String> lines = new ArrayList<>();

  default void enqueue(String message) {
    lines.add(message);
  }

  void print() throws IOException;
}