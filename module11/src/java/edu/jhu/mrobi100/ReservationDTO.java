/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jhu.mrobi100;

import java.util.Date;

/**
 *
 * @author max
 */
public class ReservationDTO {
    //contain the dates, the location of the tour, the guides name, and the first and last name on the reservation. 
    
    private Date startDate;
    private Date endDate;
    private String location;
    private String guideFirstName; 
    private String guideLastName;
    private String firstName; 
    private String lastName;
    
    private boolean error; 
    private String errorMessage;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGuideFirstName() {
        return guideFirstName;
    }

    public void setGuideFirstName(String guideFirstName) {
        this.guideFirstName = guideFirstName;
    }

    public String getGuideLastName() {
        return guideLastName;
    }

    public void setGuideLastName(String guideLastName) {
        this.guideLastName = guideLastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    
    
    
    
}
