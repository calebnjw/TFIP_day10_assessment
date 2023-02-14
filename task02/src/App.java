package task02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// look through each document
// remove all punctuation (including words like I'll and we'll?)
// split into List<String>
// go through each word
// collect current words into a map
// check if it exists in the map
// if no exist: 
//// make a new Word class for words
//// Word will store: 
//// - word &
//// - map of next words:
////// - word
////// - count
//// - total occurences of next words
// if it exists: update counts

// in "Oh the places you'll go", line 39 says "y our" instead of "your"

public class App {
  // dunno if I should be declaring variables here like that
  private static File path; // folder to look up
  private static List<File> fileList = new LinkedList<File>(); // create a list of file paths
  private static Map<String, Word> wordTrack = new TreeMap<>(); // hash map to track words across all files

  public static void main(String[] args) throws NullPointerException, IOException {
    if (args.length == 1) {
      path = new File(args[0]); // define folder

      if (!path.canRead()) { // if folder does not exist
        // tell user to enter valid directory and end program
        System.out.println("Directory does not exist. Please enter a valid folder name. ");
        return;
      }
    } else { // user did not enter a folder name or put in too many arguments
      System.out.println("please run: java -cp classes task02.App [folder name]");
      return;
    }

    for (File f : path.listFiles()) { // iterate through each file
      String oneLine = fileToString(f); // convert whole file into one cleaned string
      List<String> wordList = Arrays.asList(oneLine.split(" ")); // split into individual words

      for (int i = 0; i < wordList.size() - 1; i++) {
        String word = wordList.get(i);
        String nextWord = wordList.get(i + 1);

        if (wordTrack.containsKey(word)) { // if word already exists
          Word wordObject = wordTrack.get(word); // get Word object
          wordObject.addNextWord(nextWord); // and add next word
        } else {
          wordTrack.put(word, new Word(word)); // create new entry
          Word wordObject = wordTrack.get(word);
          wordObject.addNextWord(nextWord);
        }
      }
    }

    for (String key : wordTrack.keySet()) {
      Word data = wordTrack.get(key); // go through each word
      System.out.println(data); // and automatically call toString method
    }
  }

  public static String fileToString(File f) throws FileNotFoundException, IOException {
    String fullText = ""; // to store full text as one string and return that
    String nextLine;

    InputStream is = new FileInputStream(f);
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);

    // dump all text into a string
    while ((nextLine = br.readLine()) != null) {
      fullText += nextLine + " ";
    }

    br.close();
    isr.close();
    is.close();

    // regular expressions
    String punctuationRegex = "\\p{Punct}";
    String spacesRegex = " +";

    // strip punctuation marks and extra spaces
    fullText = fullText.trim()
        .replaceAll(punctuationRegex, "") // standard punctuation
        .replace("\u2019", "") // unicode apostrophe
        .replace("\u201C", "") // unicode double quote
        .replace("\u2026", " ") // unicode ellipsis
        .replaceAll(spacesRegex, " ") // extra spaces
        .toLowerCase(); // convert all to lowercase for easier comparison

    return fullText;
  }
}
