package com.grantburgess.battleships;

import java.util.ArrayList;
import java.util.List;

public class MockConsole implements Console {
  private List<String> messages = new ArrayList<>();
  private boolean checked = false;

  public void enqueue(String message) {
    if (checked) {
      messages.clear();
      checked = false;
    }
    messages.add(message);
  }

  @Override
  public void print() {
    // not required for mocking
  }

  public boolean check(String s) {
    checked = true;

    return messages.contains(s);
  }
}