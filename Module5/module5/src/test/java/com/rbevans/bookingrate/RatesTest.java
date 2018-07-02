/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rbevans.bookingrate;

//import java.util.GregorianCalendar;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author evansrb1
 */
public class RatesTest {

    private final BookingDay seasonStartDay = new BookingDay(2008, 6, 1);
    private final BookingDay startDay = new BookingDay(2008, 7, 1);
    private final BookingDay endDay = new BookingDay(2008, 7, 7);
    private final BookingDay startDay2 = new BookingDay(2008, 7, 2);
    private final BookingDay endDay2 = new BookingDay(2008, 7, 8);
    private final BookingDay pastEndDay = new BookingDay(2008, 11, 1);

    public RatesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getCost method, of class Rates.
     */
    @Test
    public void testGetCost() {
        System.out.println("getCost");
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        instance.setDuration(7);
        double expResult = 360.0;
        double result = instance.getCost();
        assertEquals(expResult, result, 0.001);

        // Test to see if you can re-use the Rates object
        instance.setBeginDate(startDay2);
        instance.setDuration(7);
        expResult = 360.0;
        result = instance.getCost();
        assertEquals(expResult, result, 0.001);
    }

    /**
     * Test of setSeasonStart method, of class Rates.
     */
    @Test
    public void testSetSeasonStart() {
        System.out.println("setSeasonStart");
        int month = 0;
        int day = 0;
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setSeasonStart(month, day);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of setSeasonEnd method, of class Rates.
     */
    @Test
    public void testSetSeasonEnd() {
        System.out.println("setSeasonEnd");
        int month = 0;
        int day = 0;
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setSeasonEnd(month, day);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    @Test
    public void testSetDuration() {
        // testing from start to end day
        System.out.println("setDuration");
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        instance.setDuration(7);
        BookingDay endDate = instance.getEndBookingDay();
        assertEquals(endDate, endDay);
        
        boolean badDuration = instance.setDuration(6);
        assertEquals(badDuration, false);
    }
    /**
     * Test of isValidDates method, of class Rates.
     */
    @Test
    public void testIsValidDates() {
        System.out.println("isValidDates");


        // test for null start
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        instance.setDuration(7);
        boolean expResult = true;
        boolean result = instance.isValidDates();
        assertEquals(expResult, result);

        // test for null end
        instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        expResult = false;
        result = instance.isValidDates();
        assertEquals(expResult, result);

        // test for out of season
        instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(endDay);
        instance.setEndDate(pastEndDay);
        expResult = false;
        result = instance.isValidDates();
        assertEquals(expResult, result);

        // test for end date before start
        instance = new Rates(Rates.HIKE.BEATEN);
        instance.setEndDate(startDay);
        instance.setBeginDate(endDay);
        expResult = false;
        result = instance.isValidDates();
        assertEquals(expResult, result);

        // test for invalid day
        instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        instance.setEndDate(startDay);
        expResult = false;
        result = instance.isValidDates();
        assertEquals(expResult, result);

        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getDetails method, of class Rates.
     */
    @Test
    public void testGetDetails() {
        System.out.println("getDetails");

        // test for null start
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setEndDate(endDay);
        String expResult = "One of the dates was not defined";
        instance.isValidDates();
        String result = instance.getDetails();
        assertEquals(expResult, result);

        // test for null end
        instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        expResult = "One of the dates was not defined";
        instance.isValidDates();
        result = instance.getDetails();
        assertEquals(expResult, result);

        // test for out of season
        instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(endDay);
        instance.setEndDate(pastEndDay);
        expResult = "begin or end date was out of season";
        instance.isValidDates();
        result = instance.getDetails();
        assertEquals(expResult, result);

        // test for end date before start
        instance = new Rates(Rates.HIKE.BEATEN);
        instance.setEndDate(startDay);
        instance.setBeginDate(endDay);
        expResult = "end date was before begin date";
        instance.isValidDates();
        result = instance.getDetails();
        assertEquals(expResult, result);

        // test for same day
        instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        instance.setEndDate(startDay);
        expResult = "The begin and end date must not be the same date";
        instance.isValidDates();
        result = instance.getDetails();
        assertEquals(expResult, result);

        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getNormalDays method, of class Rates.
     */
    @Test
    public void testGetNormalDays() {
        System.out.println("getNormalDays");
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        instance.setDuration(7);
        int expResult = 5;
        int result = instance.getNormalDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getPremiumDays method, of class Rates.
     */
    @Test
    public void testGetPremiumDays() {
        System.out.println("getPremiumDays");
         Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        instance.setDuration(7);
        int expResult = 2;
        int result = instance.getPremiumDays();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getBeginDate method, of class Rates.
     */
    @Test
    public void testGetBeginDate() {
        System.out.println("getBeginDate");
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        GregorianCalendar result = instance.getBeginDate();
        assertEquals(startDay.getDate(), result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The start day was not set properly in the Rates object");
    }

    /**
     * Test of getEndDate method, of class Rates.
     */
    @Test
    public void testGetEndDate() {
        System.out.println("getEndDate");
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setEndDate(endDay);
        GregorianCalendar result = instance.getEndDate();
        assertEquals(endDay.getDate(), result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The end day was not set properly in the Rates object");
    }

    /**
     * Test of getBaseRate method, of class Rates.
     */
    @Test
    public void testGetBaseRate() {
        System.out.println("getBaseRate");
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        instance.setDuration(7);
        double expResult = 45.0;
        double result = instance.getBaseRate();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getPremiumRate method, of class Rates.
     */
    @Test
    public void testGetPremiumRate() {
        System.out.println("getPremiumRate");
         Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        instance.setDuration(7);
        double expResult = 67.5;
        double result = instance.getPremiumRate();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setBeginDate method, of class Rates.
     */
    @Test
    public void testSetBeginDate() {
        System.out.println("setBeginDate");
        BookingDay beginDate = new BookingDay(2008,7,1);
        GregorianCalendar gcal = beginDate.getDate();
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(beginDate);
        assertEquals(gcal, instance.getBeginDate());
    }

    /**
     * Test of setEndDate method, of class Rates.
     */
    @Test
    public void testSetEndDate() {
        System.out.println("setEndDate");
        BookingDay beginDate = new BookingDay(2008,7,1);
        GregorianCalendar gcal = beginDate.getDate();
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setEndDate(beginDate);
        assertEquals(gcal, instance.getEndDate());
    }

    /**
     * Test of main method, of class Rates.
     */
    @Test
    public void testMain() {
        System.out.println("main");
    }

    /**
     * Test of getBeginBookingDay method, of class Rates.
     */
    @Test
    public void testGetBeginBookingDay() {
        System.out.println("getBeginBookingDay");
          Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        instance.setDuration(7);
        BookingDay result = instance.getBeginBookingDay();
        assertEquals(startDay, result);
    }

    /**
     * Test of getEndBookingDay method, of class Rates.
     */
    @Test
    public void testGetEndBookingDay() {
        System.out.println("getEndBookingDay");
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        instance.setDuration(7);
        BookingDay result = instance.getEndBookingDay();
        assertEquals(endDay, result);
    }

    /**
     * Test of getDurations method, of class Rates.
     */
    @Test
    public void testGetDurations() {
        System.out.println("getDurations");
        Rates instance = new Rates(Rates.HIKE.BEATEN);
        instance.setBeginDate(startDay);
        instance.setDuration(7);
        int[] expResult = {5, 7};
        int[] result = instance.getDurations();
        assertArrayEquals(expResult, result);
    }
}