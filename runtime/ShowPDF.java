package runtime;

public class ShowPDF {
	  public static void main(String[] args) throws Exception {
	  //  Process p =
	 //     Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler C:\\Users\\U6025719\\Desktop\\ASCE Membership Card.pdf");
	    //Process p = Runtime.getRuntime().exec("open /Documents/mypdf.pdf");  //for MAC
	   
	    //open notepad
	    //Process p2 = Runtime.getRuntime().exec("C:\\Windows\\system32\\notepad.exe");
	    
	    //start excel
	    Runtime.getRuntime().exec("cmd /c start excel.exe");
	    
	   // p.waitFor();
	    System.out.println("Done.");
	  }
	}