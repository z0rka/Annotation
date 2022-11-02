package ua.hillelit.lms.model;

/**
 * @author Kostiantyn Kvartyrmeister 01.11.2022
 */
public class Human {
  private final String name;
  private boolean processed;

  public Human(String name) {
    this.name = name;
    this.processed = false;
  }

  public String getName() {
    return name;
  }

  public boolean isProcessed() {
    return processed;
  }

  public void setProcessed(boolean processed) {
    this.processed = processed;
  }
}

