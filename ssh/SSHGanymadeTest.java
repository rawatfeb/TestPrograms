package ssh;

/*
 * Copyright (c) 2006-2011 Christian Plattner. All rights reserved.
 * Please refer to the LICENSE.txt for licensing details.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSHGanymadeTest {

	private static String hostname = "nibc1-search.amers1.ciscloud";
	private static Session sess = null;
	private static Connection conn = null;
	private static boolean isBash = false;
	private static final String USERNAME = "novus";
	private static final String PASSWORD = "n0vus!";

	public static InputStream execute(String command) throws IOException {
			if (isBash) {
				System.out.println("isBash="+isBash);
				return executeOnBash(command);
			} else {
				return executeCommand(command);
			}
	}

	private static  InputStream executeOnBash(String command) throws IOException {
		PrintWriter out = new PrintWriter(sess.getStdin());
		out.println(command.getBytes()); // commanad
		out.append(";");
		out.append("\r");
		System.out.println("command sent:");
		InputStream stdout = new StreamGobbler(sess.getStdout());
		return stdout;
	
	}

	private static void openSession(String hostname, String username, String password) throws IOException {
		/* Create a connection instance */

		conn = new Connection(hostname);

		/* Now connect */

		conn.connect();

		/*
		 * Authenticate. If you get an IOException saying something like
		 * "Authentication method password not supported by the server at this stage."
		 * then please check the FAQ.
		 */
		boolean isAuthenticated = conn.authenticateWithPassword(username, password);


		if (isAuthenticated == false)
			throw new IOException("Authentication failed.");

		/* Create a session */
		Session session = conn.openSession();
		try {
			session.requestPTY("bash");
			session.startShell();	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		isBash = true;
		sess=session;
	}

	public static void connectAsDefault(String hostname) throws IOException {
		/* opening connection as default user */
		openSession(hostname, USERNAME, PASSWORD);
	}

	private static InputStream executeCommand(String command) throws IOException {
		Session s=conn.openSession();
		s.execCommand("uname -a && date && uptime && who");
	//	sess.execCommand(command);
		System.out.println("Here is some information about the remote host:");

		/*
		 * This basic example does not handle stderr, which is sometimes
		 * dangerous (please read the FAQ).
		 */

		InputStream stdout = new StreamGobbler(s.getStdout());
		return stdout;
	}

	
	private static String readOneLine(InputStream stdout) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
		System.out.println("Reading one line only");
		String line = br.readLine();
		return line;
	}
	
	private static String readStream(InputStream is) {
		System.out.println("satrting reading stream:");
		String data = "";
		try {
			InputStream stdout = new StreamGobbler(is);
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
			String line = null;	
			while (null != (line = br.readLine())) {
				System.out.println(line);
				data = data + line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	private static void closeCommunication() {
		if (null != conn)
			conn.close();
		if (null != sess) {
			System.out.println("ExitCode:" + sess.getExitStatus());
			sess.close();
		}
	}
	public static void main(String... args) throws Exception {
		try{
		connectAsDefault(hostname);
		String command="ps -ef ; \r\n";//pwd
		 InputStream is = execute(command);
		 execute("ls -ltrh");
		 is=execute("pwd");
		//String output = readStream(is);
		 String output = readOneLine(is);
		System.out.println(output);
		}finally{
		closeCommunication();
		}
		
		
	}
	public static void main_test(String... args) {
		String hostname = "nibc1-search.amers1.ciscloud";
		String username = "root";
		String password = "!notnib";

		try {
			/* Create a connection instance */

			Connection conn = new Connection(hostname);

			/* Now connect */

			conn.connect();

			/*
			 * Authenticate. If you get an IOException saying something like
			 * "Authentication method password not supported by the server at this stage."
			 * then please check the FAQ.
			 */

			boolean isAuthenticated = conn.authenticateWithPassword(username, password);

			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");

			/* Create a session */

			Session sess = conn.openSession();

			sess.execCommand("uname -a && date && uptime && who");

			System.out.println("Here is some information about the remote host:");

			/*
			 * This basic example does not handle stderr, which is sometimes
			 * dangerous (please read the FAQ).
			 */

			InputStream stdout = new StreamGobbler(sess.getStdout());

			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}

			/* Show exit status, if available (otherwise "null") */

			System.out.println("ExitCode: " + sess.getExitStatus());

			/* Close this session */

			sess.close();

			/* Close the connection */

			conn.close();

		} catch (IOException e) {
			e.printStackTrace(System.err);
			System.exit(2);
		}
	}
}