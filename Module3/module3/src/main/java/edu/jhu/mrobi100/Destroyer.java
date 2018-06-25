package edu.jhu.mrobi100;

/*

*/
public class Destroyer extends Ship {

  private static int DEFAULT_NUMBER_MISSILES = 2;

  private int numberMissile;

  public Destroyer(int length, int speed, String name, String type, int numberMissile) {
    super(length, speed, name, type);
    if (numberMissile >= 0) {
      this.numberMissile = numberMissile;
    } else {
      this.numberMissile = 0;
    }
  }

  // <editor-fold desc="Override">
  @Override
  public String toString() {
    return "Destroyer{" + "numberMissile=" + numberMissile + ", " +  super.toString() + '}';
  }
  // </editor-fold>

  // <editor-fold desc="Properties">
  public int getNumberMissiles() {
    return numberMissile;
  }

  public void setNumberMissiles(int numberMissiles) {
    if (numberMissiles >= 0) {
      numberMissile = numberMissiles;
    } else {
      numberMissile = 0;
    }
  }

  public void setNumberMissiles(String numberMissiles) {
    try {
      numberMissile = Integer.parseInt(numberMissiles);
    } catch (Exception ex) {
      this.numberMissile = DEFAULT_NUMBER_MISSILES;
    }
  }
  // </editor-fold>
}
