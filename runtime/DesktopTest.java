package runtime;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class DesktopTest {

	public static void main(String[] args) throws Exception{

		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			for (Desktop.Action action : Desktop.Action.values()) {
				System.out.println("action " + action + " supported?  " + desktop.isSupported(action));
			}
		}
		
		File document=new File("C:\\Users\\U6025719\\Desktop\\ASCE Membership Card.pdf");
		open(document);
		URI uri=new URI("http://www.rgagnon.com");
		browse(uri);
		
	}

	// application associated to a file extension
	public static void open(File document) throws IOException {
		Desktop dt = Desktop.getDesktop();
		dt.open(document);
	}

	public static void print(File document) throws IOException {
		Desktop dt = Desktop.getDesktop();
		dt.print(document);
	}

	// default browser
	public static void browse(URI document) throws IOException {
		Desktop dt = Desktop.getDesktop();
		dt.browse(document);
	}

	// default mail client
	//   use the mailto: protocol as the URI
	//	    ex : mailto:elvis@heaven.com?SUBJECT=Love me tender&BODY=love me sweet
	public static void mail(URI document) throws IOException {
		Desktop dt = Desktop.getDesktop();
		dt.mail(document);
	}
}
