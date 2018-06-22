package edu.jhu.mrobi100;

public abstract class Ship implements Contact {
  private int length;
  private int speed;
  private String name;
  private String type;

  public Ship(int length, int speed, String name, String type) {
    this.length = length;
    this.speed = speed;
    this.name = name;
    this.type = type;
  }

  @Override
  public String toString() {
    return "Ship{" +
            "length=" + length +
            ", speed=" + speed +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            '}';
  }

  @Override
  public int getLength() {
    return length;
  }

  @Override
  public void setLength(int length) {
    if(length >= 0){
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
}
