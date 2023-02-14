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

public class App {
  public static File path;

  public static void main(String[] args) throws NullPointerException, IOException {
    if (args.length == 1) {
      path = new File(args[0]); // define folder to look up

      if (!path.canRead()) { // if folder does not exist
        // tell user to enter valid directory and end program
        System.out.println("Directory does not exist. Please enter a valid folder name. ");
        return;
      }
    } else {
      // user did not enter a folder name or put in too many arguments
      System.out.println("please run: java -cp classes task02.App [folder name]");
      return;
    }

    List<File> fileList = new LinkedList<File>(); // create a list of file paths

    Map<String, Word> wordTrack = new TreeMap<>(); // hash map to track words

    for (File f : path.listFiles()) {
      String oneLine = fileToString(f);
      List<String> wordList = Arrays.asList(oneLine.split(" "));
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
      // split into List<String>
      // go through each word
      // check if it exists in the map
      // if it doesn't, create new entry and initialise new Word()
      // add next word to Word
      // add count to Word
      // if it does, look at next word
      // look up nextWords in Word
    }

    // look through each document
    // remove all punctuation (but things like I'll and we'll should be left
    // intact?)
    // collect all words from all files into an object
    //// might wanna make a new class for words?
    //// words will store a list of words that follow
    //// and count how many of those words occur
    //// should also be able to sum up the total number of next words for
    // calculate probability

    for (String key : wordTrack.keySet()) {
      Word entry = wordTrack.get(key);
      System.out.println(entry.toString());
    }
  }

  public static String fileToString(File f) throws FileNotFoundException, IOException {
    String fullText = ""; // to store full text as one string and return that
    String nextLine;

    // read lines from file one by one
    InputStream is = new FileInputStream(f);
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);

    // dump all text into fullText
    while ((nextLine = br.readLine()) != null) {
      fullText += nextLine + " ";
    }

    br.close();
    isr.close();
    is.close();

    // regular expressions to remove punctuation but keep apostrophe
    // we'll != well
    // I'll != ill
    String punctuationRegex = "\\p{Punct}";
    String spacesRegex = " +";

    // strip punctuation marks and extra spaces
    fullText = fullText.trim()
        .replaceAll(punctuationRegex, "") // remove all punctuation
        .replace("\u2019", "") // remove apostrophe
        .replace("\u201C", "") // remove double quote
        .replace("\u2026", " ") // remove ellipsis
        .replaceAll(spacesRegex, " ") // remove extra spaces
        .toLowerCase();

    return fullText;
  }
}
