package sms.com_port;




/*You can use this free Java sample program to send SMS from your PC using GSM modem connected to your computer to your COM port. You also need to download and install the Java comm api from Sun.

This program needs the following java files to function.

1. SerialConnection.java (This file is used to connect to your COM port from your java program)

2. SerialConnectionException.java (This file is for handling serial connection exceptions in your Java program)

3. SerialParameters.java (This program is used to set your COM port properties for connecting to your com port from your java program)

4. Sender.java (This is the program that implements runnable and sends SMS using the serial connection)

5. SMSClient.java (This java class is the main class that can be instantiated in your own java program and called to send SMS. This program in turn will use all the above four files internally to send out your SMS).

*/

/*
*
* A free Java sample program 
* A list of java programs to send SMS using your COM serial connection
* and a GSM modem
*
* @author William Alexander
* free for use as long as this comment is included 
* in the program as it is
* 
* More Free Java programs available for download 
* at http://www.java-samples.com
*
*
* Note: to use this program you need to download all the 5 java files
* mentioned on top
*
*/
public class SMSClient implements Runnable {

	public final static int SYNCHRONOUS = 0;
	public final static int ASYNCHRONOUS = 1;
	private Thread myThread = null;

	private int mode = -1;
	private String recipient = null;
	private String message = null;

	public int status = -1;
	public long messageNo = -1;

	public SMSClient(int mode) {
		this.mode = mode;
	}

	public int sendMessage(String recipient, String message) {
		this.recipient = recipient;
		this.message = message;
		// System.out.println("recipient: " + recipient + " message: " +
		// message);
		myThread = new Thread(this);
		myThread.start();
		// run();
		return status;
	}

	public void run() {

		Sender aSender = new Sender(recipient, message);

		try {
			// send message
			aSender.send();

			// System.out.println("sending ... ");

			// in SYNCHRONOUS mode wait for return : 0 for OK,
			// -2 for timeout, -1 for other errors
			if (mode == SYNCHRONOUS) {
				while (aSender.status == -1) {
					myThread.sleep(1000);
				}
			}
			if (aSender.status == 0)
				messageNo = aSender.messageNo;

		} catch (Exception e) {

			e.printStackTrace();

		}

		this.status = aSender.status;

		aSender = null;

	}
}