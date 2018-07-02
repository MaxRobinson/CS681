package edu.jhu.mrobi100;

/** Class for homework 3 for webdevelopment class at JHU */
public class App {
  public static void main(String[] args) {

    try {
      int first = Integer.parseInt(args[0]);
      int second = Integer.parseInt(args[1]);
      int product = product(first, second);

      if (product <= 0) {
        System.out.println("(" + -1 * product + ")");
      } else {
        System.out.println(product);
      }

    } catch (NumberFormatException ex) {
      System.out.println("Error");
    } catch (ArrayIndexOutOfBoundsException ex) {
      System.out.println("Please provide the correct number of arguments");
    }
  }

  public static int product(int first, int second) {
    return first * second;
  }
}
