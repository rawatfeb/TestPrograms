package ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.vngx.jsch.Channel;
import org.vngx.jsch.ChannelExec;
import org.vngx.jsch.JSch;
import org.vngx.jsch.Session;
import org.vngx.jsch.config.SessionConfig;
import org.vngx.jsch.exception.JSchException;

public class VngxJschTest {

	private static final String USER="asadmin";
	private static final String HOST="nibc1-gui.amers1.cis.trcloud";
	private static final String PASS="east";
	private static final String directory = "/log";
	private static Session session;
	
	public static void main(String... args) throws Exception {

		session = getSession(USER,HOST,PASS);
		executeCommand("cd /log;ls -ltrh");
		//	test2(session,"cd /log;ls -ltrh");
	//	test2(session,"pwd");
	/*	Buffer buffer = new Buffer();
		session.read(buffer);
		System.out.println(buffer);*/
		session.disconnect();
		System.exit(0);
	}

	private static Session getSession(String user,String host,String pass) {
		JSch jsch = JSch.getInstance();
		Session session = null;
		try {
			SessionConfig config = new SessionConfig();
			config.setProperty("StrictHostKeyChecking", "no");
			session = jsch.createSession(user, host, 22, config);
			session.connect(pass.getBytes());
		} catch (JSchException e) {
			e.printStackTrace();
		}
		return session;
	}

	
	public static InputStream execute0(Channel channel,String command)throws Exception{
       ((ChannelExec)channel).setCommand(command);   //ChannelExec allows for the execution of a single command at a time and pipes the output from command to a stream.
         ((ChannelExec)channel).setErrStream(System.err);
         InputStream in=channel.getInputStream();
         channel.connect();
         return in;
	}
	
	public static StringBuilder execute(Session session,String command)throws Exception{
		StringBuilder sb=new StringBuilder();
		Channel channelShell=session.openChannel("shell");//shell
		Channel channel=session.openChannel("exec");//exec
		 InputStream in= execute0(channel, command);
		 sb=readStream(in,channel);
		 channel=session.openChannel("exec");
         channel.disconnect();	 
         return sb;
	}
	
	private static StringBuilder readStream(InputStream in, Channel channel) throws IOException {
		StringBuilder sb=new StringBuilder();
		 byte[] tmp=new byte[1024];
         while(true){
           while(in.available()>0){
             int i=in.read(tmp, 0, 1024);
             sb.append(new String(tmp));
             System.out.println(new String(tmp));
             if(i<0)break;
           }
           if(channel.isClosed()){
             System.out.println("exit-status: "+channel.getExitStatus());
             break;
           }
           try{Thread.sleep(1000);}catch(Exception ee){}
         }
		return sb;
	}

	public static void executeCommand(String command) throws Exception{
		execute(session,command);
	}
	
	
	public void test() throws Exception{
		Session session = JSch.getInstance().createSession(USER, HOST);
		Channel channel = session.openChannel("shell");

		OutputStream channel_write_stream = channel.getOutputStream();
		PrintStream commander = new PrintStream(channel_write_stream, true);

		channel.setOutputStream(System.out, true);
		// InputStream in=channel.getInputStream();
		
		
		channel.connect();

		commander.println("ls -la");    
		commander.println("cd folder");
		commander.println("ls -la");
		commander.println("exit");
		commander.close();

		do {
		    Thread.sleep(1000);
		} while(!channel.isEOF());

		session.disconnect();

	}
	
	
}
