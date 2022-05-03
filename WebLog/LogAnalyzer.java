/*
 * This program stores, prints and analyzes all entries in a weblog.
 * It retrieves the weblog entries from a file and store them in an ArrayList called records.
 * It analyzes the records to find informations such as:
 * 1. The number of unique IP addresses visiting the website.
 * 2. List entries that have status code greater than a certain number or in between ranges.
 * 3. List unique IP addresses visiting on a given day.
 * 4. List IP addresses with the most visit to the website.
 * 5. Find the day with most visits and list the IP addresses.
 *
 * @author: Lia Pratomo.
 * @date: 04/28/2022.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class LogAnalyzer {
    private ArrayList<LogEntry> records;

    // Initialize records to an empty ArrayList.
    public LogAnalyzer() {
        records = new ArrayList<>();
    }

    // Read the file line by line and store them in records.
    public void readFile(String filename) throws Exception {
        Scanner resource = new Scanner(new File(filename));

        while (resource.hasNextLine()) {
            LogEntry le = WebLogParser.parseEntry(resource.nextLine());
            records.add(le);
        }
    }

    // Print log entries in records.
    public void printAll() {
        for (LogEntry le : records) {
            System.out.println(le);
        }
    }

    // Count the number of unique IP addresses visiting the website.
    public int countUniqueIPs() {
        HashMap<String, Integer> counts = countVisitPerIP();

        return counts.size();
    }

    // Print LogEntries that have a status code greater than num.
    public void printAllHigherThanNum(int num) {
        // Access weblogs in records.
        for (LogEntry le : records) {
            if (le.getStatusCode() > num) System.out.println(le);
        }
    }

    // List unique IP addresses visiting on the given day.
    public ArrayList<String> uniqueIPVisitsOnDay(String someday) {
        ArrayList<String> result = new ArrayList<>();

        // Access weblogs in records.
        for (LogEntry le : records) {
            String currDate = le.getAccessTime().toString();
            if (currDate.contains(someday)) {
                String IpAddress = le.getIpAddress();
                if (!result.contains(IpAddress)) result.add(IpAddress);
            }
        }

        return result;
    }

    // Count the number of unique IP addresses in records that have a status code
    // in the range from low to high, inclusive.
    public int countUniqueIPsInRange(int low, int high) {
        HashSet<String> uniqueIPs = new HashSet<>();

        for (LogEntry le : records) {
            int statusCode = le.getStatusCode();

            if (statusCode >= low && statusCode <= high) {
                String IpAddress = le.getIpAddress();
                if (!uniqueIPs.contains(IpAddress)) uniqueIPs.add(IpAddress);
            }
        }

        return uniqueIPs.size();
    }

    // Map an IP address to the number of times this IP address visited the website.
    public HashMap<String, Integer> countVisitPerIP() {
        HashMap<String, Integer> counts = new HashMap<>();

        for (LogEntry le : records) {
            String IpAddress = le.getIpAddress();
            if (!counts.containsKey(IpAddress)) {
                counts.put(IpAddress, 1);
            } else {
                int val = counts.get(IpAddress);
                counts.put(IpAddress, val + 1);
            }
        }

        return counts;
    }

    // Find the maximum number of visits to this website by a single IP address.
    public int mostNumberVisitsByIP(HashMap<String, Integer> counts) {
        int max = 0;

        for (int val : counts.values()) {
            if (val > max) max = val;
        }

        return max;
    }

    // List IP addresses that all have the maximum number of visits to this website.
    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> counts) {
        ArrayList<String> res = new ArrayList<>();
        int maxVisit = mostNumberVisitsByIP(counts);

        for (String IpAddress : counts.keySet()) {
            if (counts.get(IpAddress) == maxVisit) res.add(IpAddress);
        }

        return res;
    }

    // Map days from web logs to an ArrayList of IP addresses that occurred on that day
    // (including repeated IP addresses).
    public HashMap<String, ArrayList<String>> iPsForDays() {
        HashMap<String, ArrayList<String>> res = new HashMap<>();

        for (LogEntry le : records) {
            String IPAddress = le.getIpAddress();
            String date = le.getAccessTime().toString().substring(4, 10);
            if (!res.containsKey(date)) {
                ArrayList<String> list = new ArrayList<>();
                list.add(IPAddress);
                res.put(date, list);
            } else {
                res.get(date).add(IPAddress);
            }
        }

        return res;
    }

    // Find the day that has the most IP address visits.
    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> map) {
        int max = 0;
        String mostVisit = "";
        for (String date : map.keySet()) {
            int size = map.get(date).size();
            if (size > max) {
                max = size;
                mostVisit = date;
            }
        }

        return mostVisit;
    }

    // List of IP addresses that had the most accesses on the given day.
    public ArrayList<String> iPsWithMostVisitsOnDay(
            HashMap<String, ArrayList<String>> map, String date) {
        ArrayList<String> IPList = map.get(date);
        HashMap<String, Integer> count = new HashMap<>();

        for (String IP : IPList) {
            if (!count.containsKey(IP)) count.put(IP, 1);
            else count.put(IP, count.get(IP) + 1);
        }

        return iPsMostVisits(count);
    }
}
