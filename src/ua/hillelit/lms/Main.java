package ua.hillelit.lms;

import ua.hillelit.lms.model.NamesListHandler;
import ua.hillelit.lms.model.TestRunner;
import ua.hillelit.lms.model.exceptions.TooManyAnnotations;

public class Main {

  /**
   * Main class where we test program
   */
  public static void main(String[] args) {

    NamesListHandler listHandler = new NamesListHandler(
        "C:\\Users\\kosti\\IdeaProjects\\Annotation\\src\\ua\\hillelit\\lms\\model\\source\\UsersNames");

    try {
      TestRunner.start(listHandler);
    } catch (TooManyAnnotations | NullPointerException e) {
      System.out.println(e.getMessage());
    }

  }
}