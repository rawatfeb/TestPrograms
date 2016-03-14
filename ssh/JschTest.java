package ssh;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
 
public class JschTest{
  public static void main(String[] arg){
    try{
      JSch jsch=new JSch();
 
      String host="nibc1-search.amers1.ciscloud";
      String user="novus";
      String passwd="n0vus!";
 
      Session session=jsch.getSession(user, host, 22);
      session.setPassword(passwd);
      session.setConfig("StrictHostKeyChecking",  "no");
      session.connect();

      Channel channel = session.openChannel("shell");
      /*  
       *  String command="/bin/bash";
      Channel channel=session.openChannel("exec");
      ((ChannelExec)channel).setCommand(command);
      ((ChannelExec)channel).setPty(true); */

      OutputStream inputstream_for_the_channel = channel.getOutputStream();
      PrintStream commander = new PrintStream(inputstream_for_the_channel, true);

   //   channel.setOutputStream(System.out, true);

      channel.connect();

      commander.println("ls -la");    
      commander.println("cd folder");
      commander.println("ls -la");
      commander.println("exit");
      
      
      InputStream in=channel.getInputStream();
     
    
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
      commander.close();   
    }
    catch(Exception e){
      System.out.println(e);
    }
  }
}