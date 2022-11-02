package ua.hillelit.lms.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import ua.hillelit.lms.model.annotations.AfterSuite;
import ua.hillelit.lms.model.annotations.BeforeSuite;
import ua.hillelit.lms.model.annotations.Test;
import ua.hillelit.lms.model.exceptions.TooManyAnnotations;

public class TestRunner {

  private TestRunner() {
  }

  /**
   * Checks if class has more than one {@link AfterSuite} or {@link BeforeSuite}
   *
   * @param clazz object that contains methods
   * @throws TooManyAnnotations
   */
  private static void checkSuitAnnotations(Class<?> clazz) throws TooManyAnnotations {
    List<Annotation> list = new ArrayList<>();
    Arrays.stream(clazz.getMethods())
        .filter(method -> method.isAnnotationPresent(AfterSuite.class) ||
            method.isAnnotationPresent(BeforeSuite.class))
        .forEach(method -> list.add(method.getAnnotation(AfterSuite.class)));

    long count = list.stream().distinct().count();

    if (list.size() != 2 || count != list.size()) {
      throw new TooManyAnnotations(
          clazz.getName() + " not proper amount of annotations After/Before suit");
    }
  }

  /**
   * This method creates list of methods with annotation {@link Test} also it calls method with
   * annotation {@link BeforeSuite} and finally it calls method to work with test methods
   *
   * @param clazz object that contains methods
   */
  private static <T> void testClassMethods(T clazz) {
    //Create locals (for all methods and for test methods)
    List<Method> methods = Arrays.stream(clazz.getClass().getMethods()).toList();
    List<Method> testMethods = new ArrayList<>();

    //call method  with annotation BeforeSuite , and add test methods to specific list
    methods.forEach(method -> {
      if (method.isAnnotationPresent(BeforeSuite.class)) {
        try {
          method.invoke(clazz);
        } catch (IllegalAccessException | InvocationTargetException e) {
          e.printStackTrace();
        }
      } else if (method.isAnnotationPresent(Test.class)) {
        testMethods.add(method);
      }
    });

    //Call method that invoke test methods
    testMethodsInvoker(testMethods, clazz);

    //Look for method with annotation AfterSuite and call it
    methods.forEach(method -> {
      if (method.isAnnotationPresent(AfterSuite.class)) {
        try {
          method.invoke(clazz);
        } catch (IllegalAccessException | InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Method invokes methods that are marked with annotation{@link Test}
   *
   * @param testMethods list of test methods
   * @param clazz       object that contains methods
   */
  private static <T> void testMethodsInvoker(List<Method> testMethods, T clazz) {
    //Sort by priority
    testMethods.sort(
        Comparator.comparingInt(method -> method.getAnnotation(Test.class).priority()));

    //Invoke one by one
    testMethods.forEach(method -> {
      try {
        method.invoke(clazz);
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Method that starts our test
   *
   * @param clazz object that contains methods to test
   * @throws TooManyAnnotations
   * @throws NullPointerException
   */
  public static <T> void start(T clazz) throws TooManyAnnotations, NullPointerException {
    if (clazz == null) {
      throw new NullPointerException("Object is null");
    }

    checkSuitAnnotations(clazz.getClass());

    testClassMethods(clazz);
  }

}
