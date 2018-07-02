/*
 *  Copyright (c) 2018.
 *  Max Robinson
 *  All Rights reserved.
 */

package edu.jhu.mrobi100;

import com.rbevans.bookingrate.BookingDay;
import com.rbevans.bookingrate.Rates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/** Hello world! */
public class BhcGUI extends JFrame {

  // <editor-fold desc="Statics">
  private final Integer[] YEARS = new Integer[] {2018, 2019, 2020};
  private final Integer[] MONTHS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
  private final Integer[] DAYS = {
    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,
    27, 28, 29, 30, 31
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
  public BhcGUI() {
    initValues();
    initComponents();
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
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    title = new JLabel("BHC Scheduler", SwingConstants.CENTER);

    beginDate = new JLabel("Begin Date (m/d/y)");
    hike = new JLabel("Hike");
    duration = new JLabel("Duration");
    cost = new JLabel("Cost");
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

    c.gridx = 0;
    c.gridy = GridBagConstraints.RELATIVE;

    c.anchor = GridBagConstraints.WEST;
    c.fill = GridBagConstraints.BOTH;

    c.insets = WEST_INSETS;
    add(title, c);

    add(beginDate, c);
    add(hike, c);
    add(duration, c);
    add(new JLabel(), c);
    add(cost, c);

    c.anchor = GridBagConstraints.EAST;
    c.insets = EAST_INSETS;

    c.gridx = 1;
    c.gridy = 1;
    add(month, c);
    c.gridx = 2;
    add(day, c);
    c.gridx = 3;
    add(year, c);

    c.gridx = 1;
    c.gridy = 2;
    c.gridwidth = 3;
    add(hikes, c);

    c.gridx = 1;
    c.gridy = 3;
    c.gridwidth = 3;
    add(durations, c);

    c.gridx = 2;
    c.gridy = 4;
    c.gridwidth = 3;
    add(query, c);

    c.gridx = 1;
    c.gridy = 5;
    add(totalCost, c);
  }
  // </editor-fold>

  // <editor-fold desc="Action Listeners">
  private void query() {
    // Get date
    // verify date & create dialog if not
    int m = (int) month.getSelectedItem();
    int d = (int) day.getSelectedItem();
    int y = (int) year.getSelectedItem();

    BookingDay bd = new BookingDay(y, m, d);
    if (!bd.isValidDate()) {
      JOptionPane.showMessageDialog(
          this,
          "Date is invalid, please select a valid date.",
          "Date Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    // get duration and hike, and calculate cost
    Rates.HIKE h = hikeMapping.get((String) hikes.getSelectedItem());
    Rates rates = new Rates(h);
    rates.setBeginDate(bd);
    rates.setDuration((int) durations.getSelectedItem());

    if (!rates.isValidDates()) {
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

      JOptionPane.showMessageDialog(this, message, "Trip Date Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    double value = rates.getCost();

    // set cost
    totalCost.setText("$" + value);
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
    java.awt.EventQueue.invokeLater(
        new Runnable() {
          public void run() {
            new BhcGUI().setVisible(true);
          }
        });
  }
}
