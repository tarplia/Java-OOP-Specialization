/*
 * This program has two methods to test if LogEntry and LogAnalyzer are working properly.
 * 1. testLogEntry method checks if LogEntry can store and print weblogs.
 * 2. testLogAnalyzer method checks if all methods in this class.
 *
 * @author: Lia Pratomo.
 * @date: 04/29/22.
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Tester {
    public static void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);

        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }

    public static void testLogAnalyzer() throws Exception {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("resources/weblog-short_log");

        // Print all entries in this weblog.
        System.out.println("Here are the log entries: ");
        la.printAll();

        // Count how many unique IP addresses.
        System.out.println("There are " + la.countUniqueIPs() + " unique IPs in this weblog.");

        // List all entries with status code less than the number indicated.
        int statusCode = 400;
        System.out.println("Here are log entries with Status Code higher than " + statusCode + ":");
        la.printAllHigherThanNum(statusCode);

        // Count the number of unique IP(s) on the specified date.
        String date = "Sep 30";
        System.out.println(
                "There are "
                        + la.uniqueIPVisitsOnDay(date).size()
                        + " unique IPs on "
                        + date
                        + ".");

        // Count the number of unique IPs within range provided.
        int lowerLimit = 200;
        int upperLimit = 299;
        System.out.println(
                "There are "
                        + la.countUniqueIPsInRange(lowerLimit, upperLimit)
                        + " unique IPs with status code in the range of ("
                        + lowerLimit
                        + ", "
                        + upperLimit
                        + ").");

        // List IPs with most visits to the website.
        HashMap<String, Integer> IPCount = la.countVisitPerIP();
        int maxVisit = la.mostNumberVisitsByIP(IPCount);
        System.out.println(
                "Here are IPs with most visits to the website: "
                        + la.iPsMostVisits(IPCount)
                        + ". These IPs visited "
                        + maxVisit
                        + " times.");

        // Print a map connecting dates to IPs visiting the website on that date.
        HashMap<String, ArrayList<String>> map = la.iPsForDays();
        System.out.println(map);

        // The date with the most visited IPs.
        System.out.println("Here is the date with most IP visits: " + la.dayWithMostIPVisits(map));

        // List IPs with most visits on the specified date.
        System.out.println(
                "Here are the IPs with most visits on date "
                        + date
                        + ": "
                        + la.iPsWithMostVisitsOnDay(map, date));
    }

    public static void main(String[] args) throws Exception {
        // Test if LogEntry works in storing and printing weblogs.
        testLogEntry();

        // Test if LogAnalyzer methods are working.
        testLogAnalyzer();
    }
}
