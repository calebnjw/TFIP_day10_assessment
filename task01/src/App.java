package task01;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
  public static String server;
  public static Integer port;

  public static void main(String[] args) throws IOException {
    if (args.length >= 2) {
      server = args[0];
      port = Integer.parseInt(args[1]);
    } else {
      System.out.println("please run: java -cp classes app.Main localhost 5000");
      return;
    }

    Socket s = new Socket(server, port); // open a socket listening on [server]:[port]

    InputStream is = s.getInputStream();
    ObjectInputStream ois = new ObjectInputStream(is);

    OutputStream os = s.getOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(os);

    String response = ois.readUTF();
    List<String> stringList = Arrays.asList(response.split(","));
    List<Double> doubleList = toDoubleList(stringList);

    Float mean = (float) getMean(doubleList);
    System.out.println("Mean: " + Double.toString(mean));

    Float deviation = (float) getStandardDeviation(doubleList, mean);
    System.out.println("Deviation: " + Double.toString(deviation));

    oos.writeUTF("Ng Jun Wei Caleb");
    oos.writeUTF("lelebng@gmail.com");
    oos.writeFloat(mean);
    oos.writeFloat(deviation);

    oos.close();
    os.close();

    ois.close();
    is.close();

    s.close();
  }

  public static List<Double> toDoubleList(List<String> stringList) {
    List<Double> output = new LinkedList<Double>();

    for (String s : stringList) {
      output.add(Double.parseDouble(s));
    }

    return output;
  }

  public static double getMean(List<Double> numberList) {
    Double sum = 0.0d;
    Double size = (double) numberList.size();
    Double result;

    for (Double d : numberList) {
      sum += d;
    }

    result = sum / size;
    return result;
  }

  public static double getStandardDeviation(List<Double> numberList, Float mean) {
    Double differenceSquareSum = 0.0d;
    Double size = (double) numberList.size();
    Double variance;
    Double result;

    for (Double d : numberList) {
      differenceSquareSum += Math.pow((d - mean), 2);
    }

    variance = differenceSquareSum / (size - 1);
    result = Math.sqrt(variance);
    return result;
  }
}
