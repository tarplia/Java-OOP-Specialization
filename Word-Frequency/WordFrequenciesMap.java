/**
 * This program determines which words occur in the greatest number of files,
 * and for each word, which files they occur in.
 *
 * It utilizes HashMaps to store words and their counts, and subsequently manipulate
 * them to get the answer.
 *
 * @author: Lia Pratomo
 * @date: 04/14/2022.
 */
import edu.duke.*;

import java.util.ArrayList;
import java.util.HashMap;

public class WordFrequenciesMap {
    private static HashMap<String, HashMap<String, Integer>> myMap;
    private static HashMap<String, Integer> countWords;

    public WordFrequenciesMap() {
        myMap = new HashMap<>();
        countWords = new HashMap<>();
    }

    // Build map as filename to a hashmap containing words and their count.
    private static void buildWordsToMap(String filename){
        FileResource fr = new FileResource("resources/" + filename);
        HashMap<String,Integer> map = new HashMap<>();

        // Build a map containing words and their count.
        for(String w : fr.words()){
            w = w.toLowerCase();
            if (!map.containsKey(w)){
                map.put(w,1);
            } else {
                map.put(w,map.get(w)+1);
            }
        }

        // Map filename to map.
        myMap.put(filename, map);

        // Count the number of words in the file.
        System.out.println("File name is " + filename);
        int total = 0;
        for(String w : myMap.get(filename).keySet()){
            int value = myMap.get(filename).get(w);
            total += value;
        }

        // Print out result.
        System.out.println("Total words in this file: " + total);
        System.out.println("Total unique words in this file: " + map.keySet().size());
        System.out.println("-------------------------------------------------------");
    }

    // Map words to their frequency in appearing in the files.
    private static void buildWordsCount() {
        for(String w : myMap.keySet()){
            for (String word: myMap.get(w).keySet()) {
                if (countWords.containsKey(word)) {
                    int value = countWords.get(word);
                    countWords.put(word, value + 1);
                } else {
                    countWords.put(word, 1);
                }
            }
        }
    }

    // Find the maximum number of files any word appears in.
    private static int maxNumber() {
        int max = 0;
        for (String w : countWords.keySet()) {
            int value = countWords.get(w);
            max = value > max ? value : max;
        }

        return max;
    }

    // List words that appear in exactly number files.
    private static ArrayList<String> wordsInNumFiles(int num) {
        ArrayList<String> result = new ArrayList<>();

        for (String word : countWords.keySet()) {
            int value = countWords.get(word);
            if (value == num) {
                result.add(word);
            }
        }

        return result;
    }

    // Find the names of the files this word appears in.
    private static void printFilesIn(String word) {
        for (String filename : myMap.keySet()) {
            if (myMap.get(filename).containsKey(word)) {
                System.out.println(filename);
            }
        }
    }

    private static void tester(){
        WordFrequenciesMap map = new WordFrequenciesMap();
        String[] filenames = {"caesar.txt", "confucius.txt", "errors.txt",
        "hamlet.txt", "likeit.txt", "macbeth.txt", "romeo.txt"};

        // Uncomment this if want to test single file.
        // String[] filenames = {"errors.txt"};

        for (String filename : filenames) {
            buildWordsToMap(filename);
        }

        buildWordsCount();

        System.out.println("What's the maximum number of files any word appears in? " + maxNumber());
        int noOfFiles = 6;
        System.out.println("Total words that appear " + noOfFiles + " times: " + wordsInNumFiles(noOfFiles).size());
        String word = "sea";
        System.out.println("Here are the files the word \"" + word + "\" appear in: ");
        printFilesIn(word);
    }

    public static void main(String[] args) {
        tester();
    }
}
