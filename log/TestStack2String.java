package log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TestStack2String {
  public static void main(String s[]){
    try {
      // force an exception for demonstration purpose
      Class.forName("unknown").newInstance();
      // or this could be changed to:
      //    throw new Exception();

    }
    catch (Exception e) {
      System.out.println(stack2string(e));
    }
  }

  public static String stack2string(Exception e) {
    try {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      return "------\r\n" + sw.toString() + "------\r\n";
    }
    catch(Exception e2) {
      return "bad stack2string";
    }
  }
}