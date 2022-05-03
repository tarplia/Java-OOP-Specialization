/**
 * Print out total number of babies born, as well as for each gender, in a given CSV file of baby
 * name data.
 *
 * @author Lia Pratomo
 */
import edu.duke.*;

import org.apache.commons.csv.*;

import java.io.File;

public class BabyBirths {
    private static void printNames() {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println(
                        "Name " + rec.get(0) + " Gender " + rec.get(1) + " Num Born " + rec.get(2));
            }
        }
    }

    // Find the total number of babies, baby girls and baby boys born in a specific year.
    private static void totalBirths(FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;

        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
            } else {
                totalGirls += numBorn;
            }
        }

        System.out.println("There are " + totalBirths + " babies born in TOTAL.");
        System.out.println("There are " + totalGirls + " baby GIRLS born.");
        System.out.println("There are " + totalBoys + " baby BOYS born.");
    }

    // Find the total number of baby names before the specified rank.
    private static int totalBeforeRank(FileResource fr, int rank) {
        int totalBabies = 0;
        int count = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            count++;
            int numBorn = Integer.parseInt(rec.get(2));
            if (count < rank) {
                totalBabies += numBorn;
            }
        }
        return totalBabies;
    }

    // Find the last Female rank.
    private static int lastFemalePos(FileResource fr) {
        int totalGirls = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals("F")) {
                totalGirls += 1;
            }
        }
        return totalGirls;
    }

    // Find the rank of the name in the file for the given gender.
    private static int getRank(FileResource fr, String name) {
        int count = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals("F")) {
                count++;
                if (rec.get(0).equals(name)) {
                    return count;
                }
            }
        }
        return 0;
    }

    // Find the baby name given the specified rank.
    private static String nameFromRank(FileResource fr, int rank) {
        int count = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            count++;
            if (count == rank) {
                return rec.get(0);
            }
        }
        return "";
    }

    public static void quiz() {
        // Check if totalBirths show total births and for each gender.
        String year = "2000";
        FileResource fr = new FileResource("us_babynames_by_year/yob" + year + ".csv");
        System.out.println("Here are the info on baby births in US for the year of " + year + ":");
        totalBirths(fr);

        // Check if we get the same name from rank provided.
        String name = "David";
        int rank = getRank(fr, name);
        System.out.println("What is " + name + "'s rank? " + rank);
        System.out.println("What's the name with rank of " + rank + "? " + nameFromRank(fr, rank));

        // Check the baby girl name with the same rank as the specified name above.
        rank = getRank(fr, name) + lastFemalePos(fr);
        System.out.println("Baby girl with the same rank as " + name + " is " + nameFromRank(fr, rank));

        // Check total baby boy names before the rank provided.
        name = "Augustine";
        rank = getRank(fr, name);
        System.out.println("Total babies born before the rank of " + rank + ": " + totalBeforeRank(fr, rank));
    }

    public static void quiz2() {
        int highestRank = 0;
        int count = 0;
        int initialYear = 1879;
        int result = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            count++;
            FileResource fr = new FileResource(f);
            int currRank = getRank(fr, "Mich");
            System.out.println("Mich rank = " + currRank);
            if (highestRank == 0) {
                highestRank = currRank;
            } else {
                if (currRank < highestRank) {
                    highestRank = currRank;
                    result = initialYear + count;
                }
            }
        }

        System.out.println(result);
    }

    public static void quiz3() {
        int sum = 0;
        int count = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            count++;
            FileResource fr = new FileResource(f);
            int currRank = getRank(fr, "Susan");
            System.out.println(currRank);
            sum += currRank;
        }

        System.out.println(sum / (count * 1.0));
    }

    public static void main(String[] args) {
        quiz();
        // quiz2();
        // quiz3();
    }
}
