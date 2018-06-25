package edu.jhu.mrobi100;

public class P3 extends Aircraft {

  private int numberEngines;

  public P3(int length, int speed, String name, String type, int altitude, int numberEngines) {

    super(length, speed, name, type, altitude);
    if (numberEngines >= 0) {
      this.numberEngines = numberEngines;
    } else {
      this.numberEngines = 0;
    }
  }

  @Override
  public String toString() {
    return "P3{" + "numberEngines=" + numberEngines + ", " + super.toString() + '}';
  }

  // <editor-fold desc="Properties">
  public int getNumberEngines() {
    return numberEngines;
  }

  public void setNumberEngines(int numberEngines) {
    if (numberEngines >= 0) {
      this.numberEngines = numberEngines;
    } else {
      this.numberEngines = 0;
    }
  }

  //  public void setNumberEngines(String numberEngines) {
  //    try {
  //      this.numberEngines = Integer.parseInt(numberEngines);
  //    } catch (Exception ex) {
  //      this.numberEngines = 0;
  //    }
  //  }
  // </editor-fold>
}
