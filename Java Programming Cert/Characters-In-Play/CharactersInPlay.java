import java.util.ArrayList;
import java.io.*;
import java.lang.*;

import edu.duke.*;

public class CharactersInPlay {
    private static ArrayList<String> charactersList;
    private static ArrayList<Integer> countList;

    public CharactersInPlay() {
        charactersList = new ArrayList<>();
        countList = new ArrayList<>();
    }

    // Update the two ArrayLists, adding the character’s name if it is not already there.
    private static void update(String person) {
        int index = charactersList.indexOf(person);
        if (index == -1) {
            charactersList.add(person);
            countList.add(1);
        } else {
            int value = countList.get(index);
            countList.set(index, value + 1);
        }
    }

    // Check if input String is a valid person's name.
    private static boolean validPerson(String person) {
        for (char c : person.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    // Find all characters in the play.
    private static void findAllCharacters(FileResource fr) {
        for (String line : fr.lines()) {;
            int indexOfComa = line.indexOf(",");
            int indexOfPeriod = line.indexOf(".");
            int index = 0;

            // Find the index of person's substring.
            if (indexOfComa == -1 && indexOfPeriod == -1) {
                continue;
            } else if (indexOfComa == -1) {
                index = indexOfPeriod;
            } else if (indexOfPeriod == -1) {
                index = indexOfComa;
            } else {
                index = indexOfComa < indexOfPeriod ? indexOfComa : indexOfPeriod;
            }

            String person = line.substring(0, index);

            // Check if the substring obtained is a person's name.
            if (validPerson(person)) {
                update(person);
            }
        }
    }

    private static int mostSpeakIndex() {
        int max = 0;
        int index = 0;
        for (int i = 0; i < countList.size(); i++) {
            int count = countList.get(i);
            if (count > max) {
                max = count;
                index = i;
            }
        }

        return index;
    }

    private static ArrayList<String> charactersWithSpeakLines(int min, int max) {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < countList.size(); i++) {
            int count = countList.get(i);
            if (count >= min && count <= max) {
                list.add(charactersList.get(i));
            }
        }

        return list;
    }

    public static void quiz() {
        CharactersInPlay cip = new CharactersInPlay();
        FileResource fr = new FileResource("resources/errors.txt");

        findAllCharacters(fr);
        System.out.println("Person with most speaking part is " + charactersList.get(mostSpeakIndex()));
        System.out.println("The count is " + countList.get(mostSpeakIndex()));

        int lowerLimit = 10;
        int upperLimit = 15;
        System.out.println("Characters with speaking lines in between " + lowerLimit + " and " + upperLimit + " are:");
        for (String person : charactersWithSpeakLines(lowerLimit, upperLimit)) {
            System.out.println(person);
            System.out.println("The count is: " + countList.get(charactersList.indexOf(person)));
        }
    }

    public static void main(String[] args) {
        quiz();
    }
}
