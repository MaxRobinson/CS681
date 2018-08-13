/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jhu.mrobi100;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author max
 */
public class BhcQuery {
    private static final Logger LOG = Logger.getLogger(BhcQuery.class.getName());
    private static final String REPLACE_ME = "REPLACE_ME";
    
    private final String host = "web7.jhuep.com";
    private final String dbName = "class";
    private final int port = 3306;
//    "jdbc:mysql://web7.jhuep.com:3306/class?zeroDateTimeBehavior=convertToNull"
    private final String dbURL = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
    
    private final String userName = "johncolter";
    private final String password = "LetMeIn!";
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    
    private static final String mainQuery = 
        "SELECT reservation.`First`, reservation.`Last`, reservation.StartDay, reservation.NumberOfDays, \n" +
        "locations.location, guides.`First`, guides.`Last`\n" +
        "FROM ((reservation\n" +
        "INNER JOIN locations ON reservation.location = locations.idlocations)\n" +
        "INNER JOIN guides ON reservation.guide = guides.idguides)\n" +
        "WHERE reservation.StartDay > ? \n" + 
        "ORDER BY reservation.`StartDay`" +";";

    public BhcQuery() {
    }
    
    public List<ReservationDTO> query(Date d) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch ( ClassNotFoundException cnfe) {
            System.out.println("Error loading driver " + cnfe.getMessage());
        }
        
        try {
            Connection connection = DriverManager.getConnection(dbURL, userName, password);
            PreparedStatement s = connection.prepareStatement(mainQuery);
            
            String date = sdf.format(d);
            s.setString(1, date);
            ResultSet a = s.executeQuery();
            
            return parseOutput(a);
            
        } catch (SQLException ex) {
            LOG.severe(ex.toString());
        } catch (Exception ex){
            return new ArrayList<>();
        }
        
        return new ArrayList<>();
    }
    
    private List<ReservationDTO> parseOutput(ResultSet res) throws SQLException{
        ArrayList<ReservationDTO> result = new ArrayList<>(); 
        while(res.next()){
            ReservationDTO rdto = new ReservationDTO();
            rdto.setFirstName(res.getString(1));
            rdto.setLastName(res.getString(2));
            rdto.setStartDate(res.getDate(3));
            Calendar c = Calendar.getInstance();
            c.setTime(rdto.getStartDate());
            
            c.add(Calendar.DATE, res.getInt(4));
            Date endDate = c.getTime();
            java.sql.Date d = new java.sql.Date(endDate.getTime());
            
            rdto.setEndDate(d);
            rdto.setLocation(res.getString(5));
            rdto.setGuideFirstName(res.getString(6));
            rdto.setGuideLastName(res.getString(7));
            
            result.add(rdto);
        }
        
        return result;
    }
}
