/*
 *  Copyright (c) 2018.
 *  Max Robinson
 *  All Rights reserved.
 */

package edu.jhu.mrobi100;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestClass {

  @Test
  public void main() {
    /* Set up */
    Destroyer d1 = new Destroyer(1, 1, "d1", "dest", -1);
    Destroyer d2 = new Destroyer(1, 1, "d2", "dest", 100000);

    Submarine s1 = new Submarine(1, 1, "s1", "sub", 5);
    Submarine s2 = new Submarine(1, 1, "s2", "sub", 41);

    P3 p1 = new P3(-5, 2, "p1", "p3", 2, 1);
    P3 p2 = new P3(3, -1, "p2", "p3", 23, 10);

    /* malformed test */
    d1.setNumberMissiles("SDF");
    d2.setNumberMissiles("5000");

    s1.setNumberTorpedos("-1");
    s1.setNumberTorpedos("500");

    /* Foo requirement */
    s2.setNumberTorpedos("Foo");

    /* Lists */
    List<Destroyer> destroyers = Arrays.asList(d1, d2);
    List<Submarine> submarines = Arrays.asList(s1, s2);
    List<Ship> ships = Arrays.asList(d1, d2, s1, s2);
    List<Contact> contacts = Arrays.asList(d1, d2, s1, s2, p1, p2);

    System.out.println(contacts);
  }
}
