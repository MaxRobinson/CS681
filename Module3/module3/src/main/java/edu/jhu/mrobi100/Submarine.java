package edu.jhu.mrobi100;

/** */
public class Submarine extends Ship {
  private static int DEFAULT_NUMBER_TORPEDOS = 2;

  private int numberTorpedos;

  public Submarine(int length, int speed, String name, String type,
                   int numberTorpedos) {
    super(length, speed, name, type);
    setNumberTorpedos(numberTorpedos);
  }

  @Override
  public String toString() {
    return "Submarine{" + "numberTorpedos=" + numberTorpedos + ", " +
            super.toString() + "}";
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
      int t = Integer.parseInt(numberTorpedos);
      setNumberTorpedos(t);
    } catch (Exception ex) {
      setNumberTorpedos(DEFAULT_NUMBER_TORPEDOS);
    }
  }
}
