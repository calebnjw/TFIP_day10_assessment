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

public class App {
  public static File path;

  public static void main(String[] args) throws NullPointerException, IOException {
    if (args.length == 1) {
      try {
        path = new File(args[0]); // define folder to look up

        if (!path.canRead()) { // if folder does not exist
          // tell user to enter valid directory and end program
          System.out.println("Directory does not exist. Please enter a valid folder name. ");
          return;
        }
      } catch (NullPointerException e) {
        System.out.println("Directory does not exist. Please enter a valid folder name. ");
        e.printStackTrace();
      }
    } else {
      // user did not enter a folder name or put in too many arguments
      System.out.println("please run: java -cp classes task02.App [folder name]");
      return;
    }

    // this creates a list with file paths. we want this.
    List<File> fileList = new LinkedList<File>();
    fileList = Arrays.asList(path.listFiles());
    System.out.println(fileList);

    String combinedText = "";
    for (File f : fileList) {
      String oneLine = fileToString(f);
      combinedText += oneLine + " ";
    }

    System.out.println(combinedText);

    // look through each document
    // remove all punctuation (but things like I'll and we'll should be left
    // intact?)
    // collect all words from all files into an object
    //// might wanna make a new class for words?
    //// words will store a list of words that follow
    //// and count how many of those words occur
    //// should also be able to sum up the total number of next words for
    // probability
    //

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
        .replaceAll(punctuationRegex, "")
        .replaceAll(spacesRegex, " ");

    return fullText;
  }
}
