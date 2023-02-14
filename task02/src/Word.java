package task02;

import java.util.Map;
import java.util.TreeMap;

public class Word {
  public String word;
  public Integer totalNextCount = 0;
  public Map<String, Integer> followingWords = new TreeMap<>();

  public Word(String word) {
    this.word = word;
  }

  public void addNextWord(String nextWord) {
    if (followingWords.containsKey(nextWord)) {
      int c = followingWords.get(nextWord) + 1;
      followingWords.put(nextWord, c);
    } else {
      followingWords.put(nextWord, 1);
    }
    totalNextCount += 1;
  }

  @Override
  public String toString() {
    String output = this.word;

    for (String key : followingWords.keySet()) {
      double probability = (double) followingWords.get(key) / totalNextCount;
      String probString = String.format("%.3f", probability);

      output += "\n    " + key + " " + probString;
    }

    return output;
  }
}
