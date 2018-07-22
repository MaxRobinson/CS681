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
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author max
 */
@WebServlet(name = "BhcServlet", urlPatterns = {"/BhcServlet"})
public class BhcServlet extends HttpServlet {
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
    
    private final String REPLACE_ME = "$REPLACEME$";

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
        
        String date = request.getParameter("datepicker");
        String hike = request.getParameter("Hike");
        String duration = request.getParameter("Duration");
        String numberOfPeople = request.getParameter("people");
        
        String responseMessage = query(date, hike, duration, numberOfPeople);
        
        response.setContentType("text/html;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        
        String responseString = html.replace(REPLACE_ME, responseMessage);
        
        
        try (PrintWriter out = response.getWriter()) {
            out.println(responseString);
        }
    }
    
    private String query(String date, String hike, String sDuration, String sNumberOfPeople) {
        int month;
        int day;
        int year;
        int duration; 
        int numberOfPeople;
        
        try{
            String[] dateParts = date.split("/");
            if(dateParts.length != 3){
                throw new Exception();
            }
            month = Integer.parseInt(dateParts[0]);
            day = Integer.parseInt(dateParts[1]);
            year = Integer.parseInt(dateParts[2]);
        } catch (Exception ex) {
            return "Error Parsing Date";
        }
        
        try{
            duration = Integer.parseInt(sDuration);
        } catch (Exception ex) {
            return "Error Parsing Duration";
        }
        
        try{
            numberOfPeople = Integer.parseInt(sNumberOfPeople);
            if(numberOfPeople < 1 || numberOfPeople > 10){
                throw new Exception();
            }
        } catch (Exception ex){
            return "Error parsing party size or party size is to small/large.";
        }
        
        // Get date
        // verify date & create dialog if not;
        BookingDay bd = new BookingDay(year, month, day);
        if (!bd.isValidDate()) {
            return "Date is invalid, please select a valid date.";
        }

        // get duration and hike, and calculate cost
        Rates.HIKE h = hikeMapping.get(hike);
        if(h == null){
            return "Invalide hike";
        }
        
        Rates rates = new Rates(h);
        rates.setBeginDate(bd);
        
        // validate duration
        if(hikeDurations.get(hike).contains(duration)){
            rates.setDuration(duration);
        } else {
            return "Invalid Duration for hike";
        }
        
        if (!rates.isValidDates()) {
          String message = createDatesErrorMessage(rates);
          return message;
        }

        double value = rates.getCost();
        
        double partyValue = value * numberOfPeople;

        return String.format("Total Cost: $%.2f <br> <br> Per person: $%.2f", partyValue, value);
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


    // <editor-fold defaultstate="collapsed" desc="HTML page as java String with a field to be replace by form response value">
    private final String html = "<!DOCTYPE html>\n" +
"<!--\n" +
"    Document   : index\n" +
"    Author     : Max Robinson\n" +
"-->\n" +
"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
"<html>\n" +
"  <head>\n" +
"    <!-- Link to site: https://web7.jhuep.com/mrobi100_module8/index.html -->\n" +
"\n" +
"    <title>BHC</title>\n" +
"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n" +
"    \n" +
"    <!-- Using Twitter Bootstrap for CSS Styling basics -->\n" +
"    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css\">\n" +
"    \n" +
"    <!-- Custom CSS Styling -->\n" +
"    <style type=\"text/css\">\n" +
"      .navbar a {color:white}\n" +
"      li {text-align: right; list-style: none}\n" +
"      td {text-align: center}\n" +
"      thead tr td {font-weight: bold}\n" +
"      \n" +
"    </style>\n" +
"  </head>\n" +
"  \n" +
"  <body>\n" +
"    <!-- Nav -->\n" +
"    <div class=\"nav navbar navbar-dark bg-dark\">\n" +
"      <a class=\"nav-link\" href=\"https://web7.jhuep.com/mrobi100_module8/index.html\">Home</a>\n" +
"      <a class=\"pull-right\" href=\"https://www.fs.usda.gov/recarea/shoshone/recarea/?recid=80899\">\n" +
"        Visit the Forest Service's Page\n" +
"      </a>\n" +
"    </div>\n" +
"\n" +
"    <br>\n" +
"\n" +
"    <!-- Hikes & Image Body -->\n" +
"    <div class=\"container\">\n" +
"      <div class=\"row\"> \n" +
"        <div class=\"col-md-12\">\n" +
"            <h2 style=\"text-align:center\">Welcome to Beartooth Hiking Company (BHC)</h2>\n" +
"        </div>\n" +
"      </div>\n" +
"\n" +
"      <br>\n" +
"\n" +
"      <div class=\"row\">\n" +
"        <div class=\"col-md-6\">\n" +
"            <img class=\"img-fluid\" alt=\"A picture of a train on a hill\" src=\"https://web7.jhuep.com/~evansrb1/en605681/Images/TetonTrail-2_800x600.jpg\">\n" +
"        </div>\n" +
"        <div class=\"col-md-6\">\n" +
"          <h3 style=\"text-align: right\">Tour Cost Calculator</h3>\n" +
"\n" +
"          <!--Calculator-->\n" +
"          <div class=\"col-md\">\n" +
"            <h4>" + REPLACE_ME +"</h4>\n" +
"          </div>\n" +
"\n" +
"\n" +
"        </div>\n" +
"      </div>\n" +
"      <br>\n" +
"\n" +
"      <!-- Tours -->\n" +
"      <div class=\"row\"> \n" +
"        <div class=\"col-md-12\">\n" +
"            <h2>Tours</h2>\n" +
"        </div>\n" +
"      </div>\n" +
"      <div class=\"row\">\n" +
"        <div class=\"col-md-12\">\n" +
"          <table class=\"table\">\n" +
"            <thead>\n" +
"              <tr>\n" +
"                <td>Hike</td>\n" +
"                <td>Duration (Days)</td>\n" +
"                <td>Difficulty</td>\n" +
"                <td>Price</td>\n" +
"                <td>Surcharge</td>\n" +
"              </tr>\n" +
"            </thead>\n" +
"            <tbody>\n" +
"              <tr>\n" +
"                <td> Gardiner Lake </td>\n" +
"                <td> 3 or 5 </td>\n" +
"                <td> Intermediate </td>\n" +
"                <td> $40/day </td>\n" +
"                <td> +50% for Sat/Sun Hikes </td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                <td> Hellroaring Plateau </td>\n" +
"                <td> 2, 3, or 4 </td>\n" +
"                <td> Easy </td>\n" +
"                <td> $35/day </td>\n" +
"                <td> +50% for Sat/Sun Hikes </td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                <td> The Beaten Path </td>\n" +
"                <td> 5 or 7 </td>\n" +
"                <td> Difficult </td>\n" +
"                <td> $45/day </td>\n" +
"                <td> +50% for Sat/Sun Hikes </td>\n" +
"              </tr>\n" +
"            </tbody>\n" +
"          </table>\n" +
"        </div>\n" +
"      </div>\n" +
"      <div class=\"footer\">\n" +
"          <div class=\"text-muted\" style=\"text-align: center\">Using \n" +
"            <a href=\"https://getbootstrap.com/\">Bootstrap</a>\n" +
"            for starting CSS\n" +
"          </div>\n" +
"      </div>\n" +
"    </div>\n" +
"  </body>\n" +
"</html>";
    // </editor-fold>
    
}
