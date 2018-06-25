package edu.jhu.mrobi100;

/** */
public class Submarine extends Ship {
  private static int DEFAULT_NUMBER_TORPEDOS = 2;

  private int numberTorpedos;

  public Submarine(int length, int speed, String name, String type, int numberTorpedos) {
    super(length, speed, name, type);
    if (numberTorpedos >= 0) {
      this.numberTorpedos = numberTorpedos;
    } else {
      this.numberTorpedos = 0;
    }
  }

  @Override
  public String toString() {
    return "Submarine{" + "numberTorpedos=" + numberTorpedos + ", " + super.toString() + "}" ;
  }

  public int getNumberTorpedos() {
    return numberTorpedos;
  }

  public void setNumberTorpedos(int numberTorpedos) {
    if (numberTorpedos >= 0) {
      this.numberTorpedos = numberTorpedos;
    } else {
      this.numberTorpedos = 0;
    }
  }

  public void setNumberTorpedos(String numberTorpedos) {
    try {
      this.numberTorpedos = Integer.parseInt(numberTorpedos);
    } catch (Exception ex) {
      this.numberTorpedos = DEFAULT_NUMBER_TORPEDOS;
    }
  }
}
