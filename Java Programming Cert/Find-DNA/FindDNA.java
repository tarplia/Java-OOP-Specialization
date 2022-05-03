/**
 * This program finds all proteins within strand(s) of DNA provided in the source file(s) or folder.
 * DNA is represented as a string of c,g,t,a letters. A protein is a part of the DNA strand marked
 * by specific start and stop codons (DNA triples) that is a multiple of 3 letters long.
 *
 * This program works by:
 * 1. Retrieving DNA by reading the source file line by line and store it
 * 2. Finding valid proteins according to the specified start and stop codons,
 * and continue to do so until all characters of the input string has been processed.
 * 3. All proteins will then be stored in an ArrayList.
 *
 * @author: Lia Pratomo
 * @date: 04/14/2022.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FindDNA {
    // Variations of start and stop codons.
    private static List<String> startCodons = new ArrayList<>(Arrays.asList("atg", "ttg", "ctg"));
    private static List<String> stopCodons = new ArrayList<>(Arrays.asList("tag", "taa", "tga"));

    // Find all valid protein that are marked by start and stop codons.
    public static List<String> findProtein(String dna) {
        List<String> result = new ArrayList<>();
        // Find the earliest occurance of startCodon in the DNA string.
        int start = codonIdx(dna, 0, startCodons, false);

        while (start != -1) {
            // Find the earliest occurance of stopCodon in the DNA string (after startCodon index).
            int stop = codonIdx(dna, start, stopCodons, true);
            if (stop == -1) break; // No stopCodon is found after startCodon index.

            // Get the valid protein and store it to result.
            String dnaFound = dna.substring(start, stop + 3);
            result.add(dnaFound);

            // Find the next earliest occurance of startCodon after the last valid protein found.
            start = codonIdx(dna, stop, startCodons, false);
        }

        return result;
    }

    // Find the minimum occurance of codon index from either startCodons or stopCodons variations.
    private static int codonIdx(
            String dna, int startIdx, List<String> codons, boolean isStopCodon) {
        int min = Integer.MAX_VALUE;

        for (String codon : codons) {
            int stopIdx = dna.indexOf(codon, startIdx + 3);

            // Find index of stopCodon that makes a valid protein (a multiple of 3 letters long).
            if (isStopCodon) {
                while (stopIdx != -1 && (stopIdx - startIdx) % 3 != 0) {
                    stopIdx = dna.indexOf(codon, stopIdx + 1);
                }
            }

            if (stopIdx != -1) min = Math.min(min, stopIdx);
        }

        return min == Integer.MAX_VALUE ? -1 : min;
    }

    public static int noOfGenes(String dna) {
        return findProtein(dna).size();
    }

    // Test findProtein() using large DNA strings taken from files in a folder.
    public static void testingFindProteinFromFiles(File folder) throws Exception {
        for (File file : folder.listFiles()) {
            testingFindProteinFromAFile(file);
        }
    }

    // Test findProtein() using large DNA string taken from a file.
    public static void testingFindProteinFromAFile(File file) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String dna = "";

        while (true) {
            dna = dna + br.readLine().toLowerCase();
            if (br.readLine() == null) break;
        }

        System.out.println(
                "File \"" + file.getName() + "\" has " + noOfGenes(dna) + " number of DNA.");
    }

    // Test FindProtein() using small DNA strings + edge cases.
    public static void testingFindProtein() {
        String inputDNA1 = "cccttggggttttgaaaactgtaataatgaggagagagagataagagagtttatg";
        String inputDNA2 = "";
        String inputDNA3 = "a";
        String inputDNA4 = "atg";

        List<String> expectedOutput1 =
                new ArrayList<>(Arrays.asList("ttggggttttga", "ctgtaa", "atgaggagagagagataa"));
        List<String> expectedOutput2 = new ArrayList<>();
        List<String> expectedOutput3 = new ArrayList<>();
        List<String> expectedOutput4 = new ArrayList<>();

        testAndDisplayResult(inputDNA1, expectedOutput1);
        testAndDisplayResult(inputDNA2, expectedOutput2);
        testAndDisplayResult(inputDNA3, expectedOutput3);
        testAndDisplayResult(inputDNA4, expectedOutput4);
    }

    private static void testAndDisplayResult(String inputDNA, List<String> expected) {
        List<String> result = findProtein(inputDNA);
        Collections.sort(expected);
        Collections.sort(result);

        System.out.println("Expected: " + expected);
        System.out.println("Result  : " + result);
        System.out.println(
                "Is the result the same as the expected output? " + result.equals(expected) + "\n");
    }

    public static void main(String[] args) throws Exception {
        // testingFindProtein();
        // testingFindProteinFromAFile(new File("./resources/brca1.fa"));
        testingFindProteinFromFiles(new File("./resources"));
    }
}
