package edu.jhu.mrobi100;

import org.junit.Test;
import sun.security.krb5.internal.crypto.Des;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestClass {

  @Test
  public void main() {
    Destroyer d1 = new Destroyer(1,1, "hi","who", -1);
    Destroyer d2 = new Destroyer(1,1, "hi","who", -1);

    Submarine s1 = new Submarine(1,1, "", "hello", 5);
    Submarine s2 = new Submarine(1, 1, "", "hello", 41);

    P3 p1 = new P3(1, 2, "", "", 2, 1);
    P3 p2 = new P3(3, -1, "asdf", "asdf", 23, 10);

    List<Destroyer> destroyers = Arrays.asList(d1,d2);

    List<Submarine> submarines = Arrays.asList(s1,s2);

    List<Ship> ships = Arrays.asList(d1, d2, s1, s2);

    List<Contact> contacts = Arrays.asList(d1, d2, s1, s2, p1, p2);
  }
}
