package task02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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

    // create list of all files in the folder
    // this creates a list with file names only
    // List<String> fileListString = new LinkedList<String>();
    // fileListString = Arrays.asList(path.list());
    // System.out.println(fileListString);

    // this creates a list with file paths. we want this.
    List<File> fileList = new LinkedList<File>();
    fileList = Arrays.asList(path.listFiles());
    System.out.println(fileList);

    // read file one by one using buffered reader?
    String line;
    InputStream is = new FileInputStream(fileList.get(0));
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);

    while ((line = br.readLine()) != null) {
      System.out.println(line);
    }

    br.close();
    isr.close();
    is.close();

    // strip punctuation marks
  }
}
