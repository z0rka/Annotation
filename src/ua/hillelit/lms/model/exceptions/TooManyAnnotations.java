package ua.hillelit.lms.model.exceptions;

/**
 * @author Kostiantyn Kvartyrmeister on 02.11.2022
 * Exception for {@link ua.hillelit.lms.model.TestRunner}
 */
public class TooManyAnnotations extends Exception{

  public TooManyAnnotations(String message) {
    super(message);
  }
}
