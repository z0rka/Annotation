package ua.hillelit.lms.model;

/**
 * @author Kostiantyn Kvartyrmeister 01.11.2022
 * Just class of human that contains
 * @see Human#name  of human
 * @see Human#processed  status if processed
 */
public class Human {
  private final String name;
  private final boolean processed;

  public Human(String name) {
    this.name = name;
    this.processed = false;
  }

  public Human(String name, boolean processed) {
    this.name = name;
    this.processed = processed;
  }

  public String getName() {
    return name;
  }

  public boolean isProcessed() {
    return processed;
  }

}

