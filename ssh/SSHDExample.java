package ssh;

import java.net.SocketAddress;
import java.security.PublicKey;

import org.apache.sshd.ClientSession;
import org.apache.sshd.SshClient;
import org.apache.sshd.SshServer;
import org.apache.sshd.agent.SshAgentFactory;
import org.apache.sshd.client.ServerKeyVerifier;
import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;

public class SSHDExample {
public class ServerKeyVerifierImpl implements ServerKeyVerifier {

		@Override
		public boolean verifyServerKey(ClientSession arg0, SocketAddress arg1, PublicKey arg2) {
			// TODO Auto-generated method stub
			return false;
		}

	}
public class KnownHostsManager {

	}
public static class MyPasswordAuthenticator implements PasswordAuthenticator {

		@Override
		public boolean authenticate(String arg0, String arg1, ServerSession arg2) {
			// TODO Auto-generated method stub
			return true;
		}

	}

/*public static SSHClient connect() throws Exception {
	  Map<PtyMode,Integer> tty=SttySupport.parsePtyModes(TTY);
	  SshClient client=SshClient.setUpDefaultClient();
	  client.start();
	  ClientSession session=client.connect("localhost",port).await().getSession();
	  session.authPassword("root","");
	  ChannelShell channel=(ChannelShell)session.createShellChannel();
	  channel.setPtyModes(tty);
	  PipedOutputStream out=new PipedOutputStream();
	  PipedInputStream channelIn=new PipedInputStream(out);
	  PipedOutputStream channelOut=new PipedOutputStream();
	  PipedInputStream in=new PipedInputStream(channelOut);
	  channel.setIn(channelIn);
	  channel.setOut(channelOut);
	  channel.setErr(new ByteArrayOutputStream());
	  channel.open();
	  this.channel=channel;
	  this.client=client;
	  this.session=session;
	  this.out=out;
	  this.in=in;
	  return this;
	}*/
public static SshClient create(boolean quiet){
	  SshClient client=SshClient.setUpDefaultClient();
	  SshAgentFactory agentFactory = null;
	  client.setAgentFactory(agentFactory);
	  Object knownHosts;
	 // KnownHostsManager knownHostsManager=new KnownHostsManager(knownHosts);
	  //ServerKeyVerifier serverKeyVerifier=new ServerKeyVerifierImpl(knownHostsManager,quiet);
	  //client.setServerKeyVerifier(serverKeyVerifier);
	  return client;
	}
public static void main(String...args){
	SshServer sshd = SshServer.setUpDefaultServer();
	create(true);
	/*sshd.setPort(22);
	sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider("hostkey.ser"));
	
	
	sshd.setShellFactory(new ProcessShellFactory(new String[] { "/bin/sh", "-i", "-l" }));
	sshd.setCommandFactory(new ScpCommandFactory());
	try {
		sshd.start();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	
	try {
	//	SshServer.main(args);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	sshd.setHost("tpl");
	
	sshd.setPasswordAuthenticator(new MyPasswordAuthenticator());
	
//	JaasPasswordAuthenticator pswdAuth = new JaasPasswordAuthenticator();
//	pswdAuth.setDomain("myJaasDomain");
//	sshd.setPasswordAuthenticator(pswdAuth);
*/	System.out.println("End....");
	System.exit(0);
}
}
