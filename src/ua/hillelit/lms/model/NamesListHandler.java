package ua.hillelit.lms.model;

import static java.lang.System.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import ua.hillelit.lms.model.annotations.AfterSuite;
import ua.hillelit.lms.model.annotations.BeforeSuite;
import ua.hillelit.lms.model.annotations.Test;

/**
 * @author Kostiantyn Kvartyrmeister on 30.10.2022 Class with mathods to test
 */
public class NamesListHandler {

  private List<Human> nameList = new LinkedList<>();

  /**
   * Adds to list names from source file
   *
   * @param filePath path to file with names
   */
  private void add(String filePath) {
    try {
      File file = new File(filePath);
      FileReader reader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(reader);

      String line = bufferedReader.readLine();

      while (line != null) {
        nameList.add(new Human(line));
        line = bufferedReader.readLine();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Constructor that calls add method
   *
   * @param filePath path to file with names
   */
  public NamesListHandler(String filePath) {
    if (filePath != null) {
      this.add(filePath);
    }
  }

  /**
   * When we need to put to List just one human
   *
   * @param human - object of class {@link Human}
   */
  public void add(Human human) {
    if (human != null) {
      nameList.add(human);
    }
  }

  /**
   * Adds more names from file
   *
   * @param filePath path to file with names
   */

  public void addNamesFromFile(String filePath) {
    if (filePath != null) {
      this.add(filePath);
    }

  }

  /**
   * Prints all the names
   */

  @BeforeSuite
  public void printNames() {
    nameList.forEach(human -> out.println(human.getName()));
  }

  /**
   * Method that sorts list
   */
  @Test(priority = 3)
  public void sortList() {
    nameList.sort(Comparator.comparing(Human::getName));
    out.println("Test method that sorts list!");
  }

  /**
   * Method that refactors all the names to CAPS
   */
  @Test(priority = 5)
  public void refactorNames() {
    nameList = nameList
        .stream()
        .map(human -> new Human(human.getName().toUpperCase(), true))
        .toList();

    out.println("Test method that refactors list!");
  }

  /**
   * Method shuffle list
   */
  @Test
  public void shuffle() {
    Collections.shuffle(nameList);
    out.println("Test method that shuffle list!");
  }

  /**
   * Prints all the names and its status
   */
  @AfterSuite
  public void printNamesAndStatus() {
    nameList.forEach(human -> out.println(human.getName() + " " + human.isProcessed()));
  }

  /**
   * Method gives names List
   *
   * @return List<Human>
   */
  public List<Human> getNameList() {
    return nameList;
  }
}
