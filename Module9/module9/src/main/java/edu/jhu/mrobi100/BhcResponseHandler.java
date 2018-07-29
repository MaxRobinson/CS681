/*
 *  Copyright (c) 2018.
 *  Max Robinson
 *  All Rights reserved.
 */

package edu.jhu.mrobi100;

import bookingrate.BookingDay;
import bookingrate.Rates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BhcResponseHandler implements Runnable {
  private final Socket socket;
  private static Logger LOG = Logger.getLogger(BhcResponseHandler.class.getName());

  private HashMap<Rates.HIKE, List<Integer>> hikeDurations;

  // we open June 1st
  private static final int seasonStartMonth = 6;
  private static final int seasonStartDay = 1;

  // we close Oct 1st.
  private static final int seasonEndMonth = 10;
  private static final int seasonEndDay = 1;

  public BhcResponseHandler(Socket clientSocket) {
    socket = clientSocket;

    hikeDurations = new HashMap<>();
    hikeDurations.put(Rates.HIKE.GARDINER, Arrays.asList(3, 5));
    hikeDurations.put(Rates.HIKE.HELLROARING, Arrays.asList(2, 3, 4));
    hikeDurations.put(Rates.HIKE.BEATEN, Arrays.asList(5, 7));
  }

  @Override
  public void run() {
    BufferedReader in = null;
    PrintWriter out = null;
    try {
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String outputLine = null;
      while (!socket.isClosed()) {
        outputLine = in.readLine();
        if (outputLine == null) {
          break;
        }

        try {
          String[] message = parseMessage(outputLine);
          // hikeID, begin_year, begin_month, begin_day, duration
          String response = query(message[0], message[1], message[2], message[3], message[4]);
          LOG.info(response);
          out.println(response);
        } catch (Exception ex) {
          String error = createErrorMessage("Format of Message is incorrect");
          LOG.info(error);
          out.println(error);
        }
      }
    } catch (IOException ex) {
      LOG.log(Level.SEVERE, null, ex);
    } finally {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
        if (socket != null) {
          socket.close();
        }
        LOG.info("Connection with client closed");
      } catch (IOException ex) {
        LOG.log(Level.SEVERE, null, ex);
      }
    }
  }

  private String createErrorMessage(String errorMessage) {
    return "-0.01:" + errorMessage.trim();
  }

  private String createSuccessMessage(Double cost) {
    return cost + ":Quoted Rate";
  }

  /**
   * Message should be in the form hike_id:begin_year:begin_month:begin_day:duration (e.g:
   * 1:2008:7:1:3)
   *
   * @param rawInput
   * @return
   */
  private String[] parseMessage(String rawInput) throws Exception {
    if (rawInput == null) {
      throw new Exception("Input is Null");
    }

    String[] split = rawInput.split(":");
    if (split.length != 5) {
      throw new Exception("input not formated into 5 parts with ':' separations");
    }

    return split;
  }

  private String query(String sHikeID, String sYear, String sMonth, String sDay, String sDuration) {
    int month;
    int day;
    int year;
    int duration;
    int hikeID;

    try {
      month = Integer.parseInt(sMonth);
      day = Integer.parseInt(sDay);
      year = Integer.parseInt(sYear);
    } catch (Exception ex) {
      return createErrorMessage("Error Parsing Date");
    }

    try {
      duration = Integer.parseInt(sDuration);
    } catch (Exception ex) {
      return createErrorMessage("Error Parsing Duration");
    }

    try {
      hikeID = Integer.parseInt(sHikeID);
    } catch (Exception ex) {
      return createErrorMessage("Error Parsing HikeID");
    }

    // Get date
    // verify date & create dialog if not;
    BookingDay bd = new BookingDay(year, month, day);
    if (!bd.isValidDate()) {
      return createErrorMessage("Date is invalid, please select a valid date.");
    }

    // get duration and hike, and calculate cost
    //    Rates.HIKE h = hikeMapping.get(hike);
    if (hikeID != 0 && hikeID != 1 && hikeID != 2) {
      return createErrorMessage("Invalide hike");
    }

    Rates.HIKE h = Rates.HIKE.values()[hikeID];
    Rates rates = new Rates(h);
    rates.setBeginDate(bd);

    // validate duration
    if (hikeDurations.get(h).contains(duration)) {
      rates.setDuration(duration);
    } else {
      return createErrorMessage("Invalid Duration for hike");
    }

    if (!rates.isValidDates()) {
      String message = createDatesErrorMessage(rates);
      return createErrorMessage(message);
    }

    double value = rates.getCost();

    return createSuccessMessage(value);
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
}
