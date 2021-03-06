/*
 *  Copyright (c) 2018.
 *  Max Robinson
 *  All Rights reserved.
 */

package edu.jhu.mrobi100;

import com.rbevans.bookingrate.Rates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * BhcGUI
 *
 * <p>Creates a GUI for a user to calculate how much a BHC tour would cost Uses
 * and {@link Rates} to book a day, validate the dates of the tour, and calculate the cost of the
 * tour.
 */
public class BhcGUI extends JFrame {

  // <editor-fold desc="Statics">
  private final Integer[] YEARS = new Integer[] {2018, 2019, 2020};
  private final Integer[] MONTHS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
  private final Integer[] DAYS = {
    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
    22, 23, 24, 25, 26, 27, 28, 29, 30, 31
  };
  private final String hellroaring_plateau = "Hellroaring Plateau";
  private final String the_beaten_path = "The Beaten Path";
  private final String gardiner_lake = "Gardiner Lake";
  private final String[] HIKES = {gardiner_lake, hellroaring_plateau, the_beaten_path};

  private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
  private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);

  // we open June 1st
  private static final int seasonStartMonth = 6;
  private static final int seasonStartDay = 1;

  // we close Oct 1st.
  private static final int seasonEndMonth = 10;
  private static final int seasonEndDay = 1;
  // </editor-fold>

  // <editor-fold desc="Instance Variables">
  private JLabel title;
  private JComboBox<Integer> year;
  private JComboBox<Integer> month;
  private JComboBox<Integer> day;
  private JLabel beginDate;
  private JLabel hike;
  private JLabel duration;
  private JLabel cost;
  private JLabel totalCost;
  private JComboBox<String> hikes;
  private JComboBox<Object> durations;
  private HashMap<String, List<Integer>> hikeDurations;
  private JButton query;
  private HashMap<String, Rates.HIKE> hikeMapping;
  // </editor-fold>

  // <editor-fold desc="Constructor & Init">

  /**
   * Default constructor for Bhc GUI that initializes needed values,
   * initializes the GUI components.
   */
  public BhcGUI() {
    initValues();
    initComponents();
    // Ensure a duration is selected current hike.
    hikesActionPerformed(null);
  }

  private void initValues() {
    hikeDurations = new HashMap<>();
    hikeDurations.put(gardiner_lake, Arrays.asList(3, 5));
    hikeDurations.put(hellroaring_plateau, Arrays.asList(2, 3, 4));
    hikeDurations.put(the_beaten_path, Arrays.asList(5, 7));

    hikeMapping = new HashMap<>();
    hikeMapping.put(gardiner_lake, Rates.HIKE.GARDINER);
    hikeMapping.put(hellroaring_plateau, Rates.HIKE.HELLROARING);
    hikeMapping.put(the_beaten_path, Rates.HIKE.BEATEN);
  }
  // </editor-fold>

  // <editor-fold desc="GUI Init">
  private void initComponents() {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    title = new JLabel("BHC Cost Calculator", SwingConstants.CENTER);
    title.setFont(new Font("Dialog", 1, 24));

    beginDate = new JLabel("Begin Date (m/d/y)");
    hike = new JLabel("Hike");
    duration = new JLabel("Duration");
    cost = new JLabel("Total Cost");
    totalCost = new JLabel("");
    totalCost.setHorizontalAlignment(SwingConstants.RIGHT);

    year = new JComboBox<>(YEARS);
    month = new JComboBox<>(MONTHS);
    day = new JComboBox<>(DAYS);

    hikes = new JComboBox<>(HIKES);
    hikes.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            hikesActionPerformed(e);
          }
        });

    durations = new JComboBox<>();

    query = new JButton("Get Cost");
    query.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            query();
          }
        });

    createLayout();

    pack();
  }

  private void createLayout() {
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    gbl.setConstraints(this, c);

    getContentPane().setLayout(gbl);

    JPanel p = new JPanel();
    p.setLayout(gbl);

    c.gridx = 0;
    c.gridy = GridBagConstraints.RELATIVE;
    c.fill = GridBagConstraints.BOTH;
    c.insets = WEST_INSETS;

    c.anchor = GridBagConstraints.WEST;
    p.add(beginDate, c);
    p.add(hike, c);
    p.add(duration, c);
    p.add(new JLabel(), c);
    p.add(cost, c);

    c.anchor = GridBagConstraints.EAST;
    c.insets = EAST_INSETS;

    c.gridx = 1;
    c.gridy = 0;
    p.add(month, c);
    c.gridx = 2;
    p.add(day, c);
    c.gridx = 3;
    p.add(year, c);

    c.gridx = 1;
    c.gridy = 1;
    c.gridwidth = 3;
    p.add(hikes, c);

    c.gridx = 1;
    c.gridy = 2;
    c.gridwidth = 3;
    p.add(durations, c);

    c.gridx = 2;
    c.gridy = 3;
    c.gridwidth = 3;
    p.add(query, c);

    c.gridx = 1;
    c.gridy = 4;
    p.add(totalCost, c);

    c.insets = new Insets(15, 15, 0, 15);

    c.gridx = 1;
    c.gridy = 0;
    c.anchor = GridBagConstraints.NORTH;
    add(title, c);

    c.insets = new Insets(15, 15, 15, 15);
    c.gridx = 1;
    c.gridy = 1;
    c.anchor = GridBagConstraints.NORTH;
    add(p, c);
  }
  // </editor-fold>

  // <editor-fold desc="Action Listeners">
  private void query() {
    totalCost.setText("");
    // Get date
    // verify date & create dialog if not
    int m = (int) month.getSelectedItem();
    int d = (int) day.getSelectedItem();
    int y = (int) year.getSelectedItem();
    Rates.HIKE h = hikeMapping.get((String) hikes.getSelectedItem());
    int duration = (int) durations.getSelectedItem();

    String message = createRequestMessage(h.ordinal(), y, m, d, duration);

    try(Socket s = new Socket("web7.jhuep.com", 20025)){
      PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
      BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
      pw.println(message);

      String response = br.readLine();

      String[] values = parseResponse(response);
      if(values == null){
        createErrorMessage("Error in response message, please try again");
        return;
      }

      boolean error = isErrorResponse(values);
      if(error) {
        createErrorMessage(values[1]);
        return;
      }

      String value = values[0];
      // set cost
      totalCost.setText("$" + value);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(
          this,
          "Date is invalid, please select a valid date.",
          "Date Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void createErrorMessage(String message) {
    JOptionPane.showMessageDialog(
          this,
          message,
          "Error",
          JOptionPane.ERROR_MESSAGE);
  }

  private boolean isErrorResponse(String[] values) {
    String cost = values[0];
    return "-0.01".equals(cost);
  }

  /**
   * Parses the response from server.
   *
   * The returned result will be the cost followed by a ":", followed by some text
   * If things go well, you'll get the cost and the text "Quoted Rate",
   * if there is a problem, the cost will by -0.01 and the text will have some explanation
   * @param response
   * @return
   */
  private String[] parseResponse(String response) {
    String[] splits = response.split(":");
    if (splits.length < 2) {
      return null;
    }
    return splits;
  }

  /**
   * Create Message in following format
   *
   * hike_id:begin_year:begin_month:begin_day:duration (e.g: 1:2008:7:1:3)
   *
   * Gardiner Lake is hike_id 0, with durations of 3 or 5 days
   * Hellroaring Plateu is hike_id 1, with durations of 2, 3, or 4 days
   * Beaten Path is hike_id 2, with durations of 5 or 7 days
   * @return
   */
  private String createRequestMessage(int hike_id, int begin_year,
                                      int begin_month, int begin_day,
                                      int duration) {
    return String.format("%d:%d:%d:%d:%d", hike_id, begin_year,
            begin_month, begin_day, duration);
  }

  private void hikesActionPerformed(ActionEvent e) {
    durations.removeAllItems();
    String selectedItem = (String) hikes.getSelectedItem();
    for (Integer durs : hikeDurations.get(selectedItem)) {
      durations.addItem(durs);
    }
    repaint();
  }
  // </editor-fold>

  public static void main(String[] args) {
    EventQueue.invokeLater(
        new Runnable() {
          public void run() {
            new BhcGUI().setVisible(true);
          }
        });
  }
}
