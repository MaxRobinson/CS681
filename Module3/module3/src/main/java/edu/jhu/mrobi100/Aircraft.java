package edu.jhu.mrobi100;

/** */
public abstract class Aircraft implements Contact {

  private int length;
  private int speed;
  private String name;
  private String type;
  private int altitude;

  public Aircraft(int length, int speed, String name, String type, int altitude) {
    if (length >= 0) {
      this.length = length;
    } else {
      this.length = 0;
    }

    this.speed = speed;
    this.name = name;
    this.type = type;

    if (altitude >= 0) {
      this.altitude = altitude;
    } else {
      this.altitude = altitude;
    }
  }

  // <editor-fold desc="Override">


  @Override
  public String toString() {
    return "Aircraft{" +
            "length=" + length +
            ", speed=" + speed +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", altitude=" + altitude +
            '}';
  }

  @Override
  public int getLength() {
    return length;
  }

  @Override
  public void setLength(int length) {
    if (length >= 0) {
      this.length = length;
    } else {
      this.length = 0;
    }
  }

  @Override
  public int getSpeed() {
    return speed;
  }

  @Override
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  @Override
  public void setSpeed(String speed) {
    try {
      this.speed = Integer.parseInt(speed);
    } catch (Exception ex) {
      this.speed = 0;
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public void setType(String type) {
    this.type = type;
  }

  // </editor-fold>

  // <editor-fold desc="Properties">
  public int getAltitude() {
    return altitude;
  }

  public int setAltitude(int altitude) {
    return altitude;
  }
  // </editor-fold>
}
