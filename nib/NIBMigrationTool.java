package nib;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.sshd.ClientChannel;
import org.apache.sshd.ClientSession;
import org.apache.sshd.SshClient;
import org.apache.sshd.client.future.AuthFuture;
import org.apache.sshd.common.future.SshFutureListener;
import org.apache.sshd.common.util.NoCloseInputStream;
import org.apache.sshd.common.util.NoCloseOutputStream;

public class NIBMigrationTool {

	
	public static void main(String...args) throws Throwable{
		
		String newNIBHostName="nibc1-load.amers1.cis.trcloud";
		String modelNIBHostName="";
		String user="novus";
		String password="n0vus!";
		
		
		connectSSHD(user,password,newNIBHostName);
		
		
		
		
//		createUsers();
//		createDirectories();
//		crossCheck();
//		verifyPermission();
//		copyReleases();
//		copyData();
//		startAndStopServices();
	}

	private static void connectSSHD(String user,String password,String host) throws InterruptedException, IOException {
	//	System.setProperty("javax.net.ssl.trustStore","/my_path/truststore.jks");
	//	System.setProperty("javax.net.ssl.trustStorePassword","n0vus!");
		
		
		SshClient client = SshClient.setUpDefaultClient();
		    client.start();

		    final ClientSession session = client.connect(user, host, 22).await().getSession();

		    int authState = ClientSession.WAIT_AUTH;
		    while ((authState & ClientSession.WAIT_AUTH) != 0) {

		        session.addPasswordIdentity(password);

		        System.out.println("authenticating...");
		        final AuthFuture authFuture = session.auth();
		        authFuture.addListener(new SshFutureListener<AuthFuture>()
		        {
		            @Override
		            public void operationComplete(AuthFuture arg0)
		            {
		                System.out.println("Authentication completed with " + ( arg0.isSuccess() ? "success" : "failure"));
		            }
		        });

		        authState = session.waitFor(ClientSession.WAIT_AUTH | ClientSession.CLOSED | ClientSession.AUTHED, 0);
		    }

		    if ((authState & ClientSession.CLOSED) != 0) {
		        System.err.println("error");
		        System.exit(-1);
		    }

		    final ClientChannel channel = session.createShellChannel();
		    channel.setOut(new NoCloseOutputStream(System.out));
		    channel.setErr(new NoCloseOutputStream(System.err));
		    channel.open();

		    executeCommand(channel, "pwd\n");
		    executeCommand(channel, "ll\n");
		    channel.waitFor(ClientChannel.CLOSED, 0);

		    session.close(false);
		    client.stop();
		}

		
		
		private static void executeCommand(final ClientChannel channel, final String command) throws IOException
		{
		    final InputStream commandInput = new ByteArrayInputStream(command.getBytes());
		    channel.setIn(new NoCloseInputStream(commandInput));
		}

	private static void verifyPermission() {
		// TODO Auto-generated method stub
		
	}

	private static void startAndStopServices() {
		// TODO Auto-generated method stub
		
	}

	private static void copyData() {
		// TODO Auto-generated method stub
		
	}

	private static void copyReleases() {
		// TODO Auto-generated method stub
		
	}

	private static void crossCheck() {
		// TODO Auto-generated method stub
		
	}

	private static void createDirectories() {
		// TODO Auto-generated method stub
		
	}

	private static void createUsers() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
