package edu.jhu.mrobi100;

/* 
 * copyright :) 
 */

public interface Contact {
    /* getters / Setters */
    int getLength();
    void setLength(int length);
    
    int getSpeed();
    void setSpeed(int x);
    void setSpeed(String speed);
    
    String getName();
    void setName(String name);
    
    String getType();
    void setType(String type);

} 
