package task02;

import java.util.Map;
import java.util.TreeMap;

public class Word {
  public String word; // the current word
  public Integer totalNextCount = 0; // total number of next words
  public Map<String, Integer> followingWords = new TreeMap<>(); // tracking next words and their counts

  public Word(String word) {
    this.word = word;
  }

  public void addNextWord(String nextWord) {
    if (followingWords.containsKey(nextWord)) { // checks for nextWord
      int c = followingWords.get(nextWord) + 1;
      followingWords.put(nextWord, c);
    } else {
      followingWords.put(nextWord, 1);
    }
    totalNextCount += 1; // increases total next word count
  }

  @Override
  public String toString() {
    String output = this.word; // current word

    for (String key : followingWords.keySet()) { // and print out the next words
      double probability = (double) followingWords.get(key) / totalNextCount;
      String probString = String.format("%.3f", probability);

      output += "\n    " + key + " " + probString;
    }

    return output;
  }
}
