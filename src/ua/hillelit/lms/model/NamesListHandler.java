package ua.hillelit.lms.model;

import static java.lang.System.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
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

  public NamesListHandler(String filePath) {
    if(filePath != null) {
      this.add(filePath);
    }
  }

  /**
   * Prints all the names
   */

  @BeforeSuite
  public void beforeTest() {
    nameList.forEach(human -> out.println(human.getName()));
  }

  /**
   * Method that sorts list
   */
  @Test(priority = 1)
  public void sortList() {
    nameList.sort(Comparator.comparing(Human::getName));
    out.println("Test method that sorts list!");
  }

  /**
   * Method that refactors all the names to CAPS
   */
  @Test(priority = 2)
  public void refactorNames() {
    nameList = nameList.stream().map(human -> new Human(human.getName().toUpperCase())).toList();
  }


  /**
   * Method delete all the names that contain specific line
   */
  @Test(priority = 3)
  public void deleteNamesWithA(String line) {
    nameList = nameList.stream().filter(human -> !human.getName().contains(line))
        .peek(human -> human.setProcessed(true)).toList();
  }

  /**
   * Prints all the names and its priority
   */
  @AfterSuite
  public void afterTest() {
    nameList.forEach(human -> out.println(human.getName() + " " + human.isProcessed()));
  }

  public List<Human> getNameList() {
    return nameList;
  }
}
