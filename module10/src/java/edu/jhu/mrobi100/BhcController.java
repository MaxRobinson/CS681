/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jhu.mrobi100;

import com.rbevans.bookingrate.BookingDay;
import com.rbevans.bookingrate.Rates;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author max
 */
@WebServlet(name = "BhcController", urlPatterns = {"/BhcController"})
public class BhcController extends HttpServlet {
    private final String hellroaring_plateau = "Hellroaring Plateau";
    private final String the_beaten_path = "The Beaten Path";
    private final String gardiner_lake = "Gardiner Lake";
    private HashMap<String, List<Integer>> hikeDurations;
    private HashMap<String, Rates.HIKE> hikeMapping;
    
    
    // we open June 1st
    private static final int seasonStartMonth = 6;
    private static final int seasonStartDay = 1;
 
    // we close Oct 1st.
    private static final int seasonEndMonth = 10;
    private static final int seasonEndDay = 1;
    
    @Override
    public void init(){
        hikeDurations = new HashMap<>();
        hikeDurations.put(gardiner_lake, Arrays.asList(3, 5));
        hikeDurations.put(hellroaring_plateau, Arrays.asList(2, 3, 4));
        hikeDurations.put(the_beaten_path, Arrays.asList(5, 7));
        
        hikeMapping = new HashMap<>();
        hikeMapping.put(gardiner_lake, Rates.HIKE.GARDINER);
        hikeMapping.put(hellroaring_plateau, Rates.HIKE.HELLROARING);
        hikeMapping.put(the_beaten_path, Rates.HIKE.BEATEN);
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ServletContext servletContext = getServletContext();
        
        Hike hike = new Hike();
        hike.setDate(request.getParameter("datepicker"));
        hike.setHikeName(request.getParameter("Hike"));
        hike.setDuration(request.getParameter("Duration"));
        hike.setNumberOfPeople(request.getParameter("people"));
        
        hike = query(hike);
        
        request.setAttribute("hikeSubmit", hike);
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/ResultPage.jsp");
        dispatcher.forward(request, response);
    }
    
//    private String query(String date, String hike, String sDuration, String sNumberOfPeople) {
    private Hike query(Hike hike) {
        int month;
        int day;
        int year;
        int duration; 
        int numberOfPeople;
        
        try{
            String[] dateParts = hike.getDate().split("/");
            if(dateParts.length != 3){
                throw new Exception();
            }
            month = Integer.parseInt(dateParts[0]);
            day = Integer.parseInt(dateParts[1]);
            year = Integer.parseInt(dateParts[2]);
        } catch (Exception ex) {
            hike.setErrorMessage("Error Parsing Date");
            return hike;
        }
        
        try{
            duration = Integer.parseInt(hike.getDuration());
        } catch (Exception ex) {
            hike.setErrorMessage("Error Parsing Duration");
            return hike;
        }
        
        try{
            numberOfPeople = Integer.parseInt(hike.getNumberOfPeople());
            if(numberOfPeople < 1 || numberOfPeople > 10){
                throw new Exception();
            }
        } catch (Exception ex){
            hike.setErrorMessage("Error parsing party size or party size is to small/large.");
            return hike;
        }
        
        // Get date
        // verify date & create dialog if not;
        BookingDay bd = new BookingDay(year, month, day);
        if (!bd.isValidDate()) {
            hike.setErrorMessage("Date is invalid, please select a valid date.");
            return hike;
        }

        // get duration and hike, and calculate cost
        Rates.HIKE h = hikeMapping.get(hike.getHikeName());
        if(h == null){
            hike.setErrorMessage("Invalid hike");
            return hike;
        }
        
        Rates rates = new Rates(h);
        rates.setBeginDate(bd);
        
        // validate duration
        if(hikeDurations.get(hike.getHikeName()).contains(duration)){
            rates.setDuration(duration);
        } else {
            hike.setErrorMessage("Invalid Duration for hike");
            return hike;
        }
        
        if (!rates.isValidDates()) {
          String message = createDatesErrorMessage(rates);
          hike.setErrorMessage(message);
          return hike;
        }

        double value = rates.getCost();
        
        double partyValue = value * numberOfPeople;
        hike.setCost(Double.toString(partyValue));

        return hike;
      }
    
    private String createDatesErrorMessage(Rates rates) {
        String message = "";
        if (rates.getDetails().contains("out of season")) {

          if (rates.getBeginBookingDay().before(seasonStartMonth, seasonStartDay)) {
            message =
                "Start date is before the season opens. Please select a start date after "
                    + seasonStartMonth
                    + "/"
                    + seasonStartDay;
          } else if (rates.getBeginBookingDay().after(seasonEndMonth, seasonEndDay)) {
            message =
                "Start date is after the season closes. Please select a start date before "
                    + seasonEndMonth
                    + "/"
                    + seasonEndDay;
          } else if (rates.getEndBookingDay().after(seasonStartMonth, seasonStartDay)) {
            message =
                "End date is after the season closes. Please select a start date and duration that ends before "
                    + seasonEndMonth
                    + "/"
                    + seasonEndDay;
          } else if (rates.getEndBookingDay().before(seasonStartMonth, seasonStartDay)) {
            message =
                "End date is before the season opens. Please select a start date that after "
                    + seasonStartMonth
                    + "/"
                    + seasonStartDay;
          }
        }
        return message;
      }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
