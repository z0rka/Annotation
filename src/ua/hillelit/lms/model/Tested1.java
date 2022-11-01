package ua.hillelit.lms.model;

import ua.hillelit.lms.model.annotations.Test;

/**
 * @author Kostiantyn Kvartyrmeister on 30.10.2022
 * Class with mathods to test
 */
public class Tested1 {

  private String name;

  public Tested1(String name) {
    this.name = name;
  }

  /**Method that we have to test*/
  @Test(priority = 1)
  public void methodToTest(){
    this.name  = name.toUpperCase();
    System.out.println(toString());
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Tested1{" +
        "name='" + name + '\'' +
        '}';
  }

}
