package sound;
import java.awt.Toolkit;

public class WindowsSounds {
  public static void main(String ... args) throws InterruptedException {
    System.out.println("Sound 1");
    Runnable sound1 =
      (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
    if(sound1 != null) sound1.run();
    Thread.sleep(1000);
    System.out.println("Sound 2");
    Runnable sound2 =
      (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
    if(sound2 != null) sound2.run();

    System.out.println("Supported windows property names:");
    String propnames[] = (String[])Toolkit.getDefaultToolkit().getDesktopProperty("win.propNames");
    for(int i = 0; i < propnames.length; i++) {
      if (propnames[i].startsWith("win.sound.")) {
        System.out.println(propnames[i]);
      }
    }
  }
}
/*
   output :
Sound 1
Sound 2
Supported windows property names:
win.sound.asterisk
win.sound.close
win.sound.default
win.sound.exclamation
win.sound.exit
win.sound.hand
win.sound.maximize
win.sound.menuCommand
win.sound.menuPopup
win.sound.minimize
win.sound.open
win.sound.question
win.sound.restoreDown
win.sound.restoreUp
win.sound.start

*/