package nib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class NIBTools {
	private static String USER = "novus";
	private static String PASSWORD = "n0vus!";
	private static Channel channel =null;
	private static Session session=null;
	private static String newNIBHostName= "nibc1-load.amers1.cis.trcloud";
	
	
	
	public static void main(String... args) throws Throwable {

	//	String newNIBHostName = "nibc1-load.amers1.cis.trcloud";
		String modelNIBHostName = "";
		connect();
		openChannel();
		String command="ps -ef|grep LoadMon";
		InputStream in = execute(command);
	//	readStraem(in);
		 Thread readThread = new Thread(new ReadThread(in));
		 readThread.setName("ReadStreamThread");
		 readThread.start();
		Thread.sleep(2000);
		channel.disconnect();
	
		
		
		
		createUsers();
		createDirectories();
		crossCheck();
		verifyPermission();
		copyReleases();
		copyData();
		startAndStopServices();
		
		
		
		/*1. one thread execute command another thread read Stream after executing command sleep for sometime and close the channel. another thread will read the stream until channel is closed */
		/*2.  other way is to write output of command to temporary location and reads from there. */
		
		
		
	}

	private static void openChannel() throws JSchException {
		channel=session.openChannel("shell");
		System.out.println("channelID:="+channel.getId());
	}

	private static void connect() throws Exception   {
		session = getSession();
	}

	

	private static InputStream execute(String command) throws Exception {
		
		OutputStream to_console_stream_ = channel.getOutputStream();
		PrintStream exec = new PrintStream(to_console_stream_, true);

		// channel.setOutputStream(System.out, true);
		channel.connect();
	/*	exec.println("ls -la");
		exec.println("cd folder");
		exec.println("ls -la");*/
		exec.println(command);
		//exec.println("exit");
		InputStream in = channel.getInputStream();
		//exec.close();
		return in;
	}
	
	private static void readStraem(InputStream in) throws IOException{
		 byte[] tmp=new byte[1024];
	      while(true){
	        while(in.available()>0){
	          int i=in.read(tmp, 0, 1024);
	          if(i<0)break;
	          System.out.print(new String(tmp, 0, i));
	        }
	        if(channel.isClosed()){
	          System.out.println("exit-status: "+channel.getExitStatus());
	          break;
	        }
	        try{Thread.sleep(1000);}catch(Exception ee){}
	      }
	      System.out.println("finished");
	      channel.disconnect();
	      session.disconnect(); 
	}
	
	
	private static class ReadThread implements Runnable{
		InputStream in=null;
		ReadThread(InputStream in){
			this.in=in;
		}
		
	@Override
	public void run() {
		 byte[] tmp=new byte[1024];
	      while(true){
	        try {
				while(in.available()>0){
				  int i=in.read(tmp, 0, 1024);
				  if(i<0)break;
				  System.out.print(new String(tmp, 0, i));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean exit=false;
			/*if(channel.isClosed()){
	          System.out.println("exit-status: "+channel.getExitStatus());
	          break;
	        }*/
	        if(exit){
		          System.out.println("exit-status: "+channel.getExitStatus());
		          break;
		        }
	        
	        try{Thread.sleep(1000);}catch(Exception ee){}
	      }
	      System.out.println("finished");
	      channel.disconnect();
	      session.disconnect(); 
		
	}
		
	}
	
	

	private static Session getSession() throws Exception {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(USER, newNIBHostName, 22);
			session.setPassword(PASSWORD);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			return session;
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new Exception("Not able to get session");
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

	private static void verifyPermission() {
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
