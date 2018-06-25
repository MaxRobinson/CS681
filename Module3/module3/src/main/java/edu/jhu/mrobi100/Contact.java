/*
 *  Copyright (c) 2018.
 *  Max Robinson
 *  All Rights reserved.
 */

package edu.jhu.mrobi100;

public interface Contact {

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
