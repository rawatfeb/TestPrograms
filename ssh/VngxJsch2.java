package ssh;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.vngx.jsch.Channel;
import org.vngx.jsch.JSch;
import org.vngx.jsch.Session;
import org.vngx.jsch.config.SessionConfig;
import org.vngx.jsch.exception.JSchException;

public class VngxJsch2 implements Runnable {

	private static final String USER = "asadmin";
	private static final String PASS = "east";
	private static final String TARGET_HOST = "nibc1-load.amers1.ciscloud";
	private static final String SEARCH_NODE_TYPE = "search";
	private static final String LOAD_NODE_TYPE = "load";
	private static final String BASE = "nibc1-load.amers1.cis.trcloud";
	private static Session session = null;
	private static Channel channel = null;

	private static InputStream commandIS = null;
	private static OutputStream writeOS = null;

	public static void main(String... args) throws Exception {
		VngxJsch2 vngx = new VngxJsch2();
		/*
		 * new Thread(vngx,"WriterThread").start(); new
		 * Thread(vngx,"CommandThread").start();
		 */

		openSession();
		PrintStream executor = openChannel();
		executeCommand(executor, "ls -ltrh");
		executeCommand(executor, "cd /log");
		executeCommand(executor, "ls -ltrh");
		executeCommand(executor, "date");
		executor.println("exit");
		executor.close();
		channel.disconnect();
		session.disconnect();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("thread");
		System.out.println(Thread.currentThread().getName());
		System.out.println();
		Thread.currentThread().interrupt();
	}

	private static void executeCommand(PrintStream executor, String command)
			throws InterruptedException {
		executor.println("ls -la");
		executor.println("cd folder");
		executor.println(command);
		// executor.println("exit");
	}

	private static PrintStream openChannel() throws JSchException, IOException {
		channel = session.openChannel("shell");
		OutputStream channel_write_stream = channel.getOutputStream();
		PrintStream commander = new PrintStream(channel_write_stream, true);
		// channel.setOutputStream(System.out, true);
		writeOS = new FileOutputStream("NIB_Creation_logs.log");
		channel.setOutputStream(writeOS, true);
		channel.connect();
		return commander;
	}

	private static void openSession() throws JSchException,
			InterruptedException, IOException {
		SessionConfig config = new SessionConfig();
		config.setProperty("StrictHostKeyChecking", "no");
		session = JSch.getInstance().createSession(USER, TARGET_HOST, 22,
				config);
		session.connect(PASS.getBytes());
	}

	private static void verifyPermission() {
		// TODO Auto-generated method stub
		// read the log file
	}

	private static void startServices(PrintStream executor)
			throws InterruptedException {
		StringBuilder command = new StringBuilder();
		command.append("/novus/users/nsa/bin/master-ctl start;");
		executeCommand(executor, command.toString());
	}

	private static void copyReleases(PrintStream executor)
			throws InterruptedException {
		StringBuilder command = new StringBuilder();
		command.append("rm -rf /novus/nib/releases/Novus /novus/nib/releases/System /novus/nib/releases/MQ /novus/nib/releases/Lang /novus/nib/releases/db /novus/nib/releases/java ;");
		command.append("scp -r novus@"+BASE+":/novus/client/releases/Novus.15.2.3.1.jdk1.7 /novus/releases/ ; scp -r novus@"+BASE+":/novus/client/releases/System.20141118 /novus/releases/ ; scp -r novus@"+BASE+":/novus/client/releases/Lang.20140221 /novus/releases/ ; scp -r novus@"+BASE+":/novus/jdk/jdk1.7.0_67 /novus/jdk/ ;  scp -r novus@"+BASE+":/novus/jdk/jdk1.7.0_67.x64 /novus/jdk/ ;");
		executeCommand(executor, command.toString());
	}

	private static void installNovusD(PrintStream executor)
			throws InterruptedException {
		StringBuilder command = new StringBuilder();
		command.append("mount nssinfrashare.westlan.com:/vol/infra_nss_snap/nssinfralv/infra  /tools/infra; mount onlineservices.westlan.com:/vol/infra_online_srvcs_snap/novusrelv/novusRelease  /tools/novusRelease;rpm -ivh --replacepkgs /tools/novusRelease/codist/1.2.5/codist-1.2.5-1.ol6.x86_64.rpm;");
		executeCommand(executor, command.toString());
	}

	private static void crossCheck(PrintStream executor) throws InterruptedException {
		StringBuilder command = new StringBuilder();
		command.append("");
		executeCommand(executor, command.toString());

	}

	private static void createNovusDirectories(PrintStream executor,
			String nodeType) throws InterruptedException {
		StringBuilder command = new StringBuilder();
		if (LOAD_NODE_TYPE.equalsIgnoreCase(nodeType)) {
			command.append("rm -rf /novus/index /novus/data /novus/indexwheel /novus/novusdev ;");
			command.append("mkdir /novus ; mkdir  /novus/index ; mkdir /novus/data; mkdir /novus/indexwheel; mkdir  /novus/novusdev ;");
			command.append("ln -s /mnt/nas/index index ; ln -s /mnt/nas/data data ; ln -s /mnt/nas/indexwheel indexwheel ; ln -s /novus/users/novusdev novusdev; chown -R novusdev:novus1 /novus/novusdev ;");
		}

		executeCommand(executor, command.toString());
	}

	private static void migrateNovusData(PrintStream executor, String nodeType)
			throws InterruptedException {
		StringBuilder command = new StringBuilder();
		if (LOAD_NODE_TYPE.equalsIgnoreCase(nodeType)) {
			command.append("scp -r root@"
					+ BASE
					+ ":/novus/data/* /novus/data/ ; scp -r root@"
					+ BASE
					+ ":/novus/index/* /novus/index/   ; scp -r root@"
					+ BASE
					+ ":/novus/indexwheel/* /novus/indexwheel/ ; chown -R nib:novus1 /novus/index /novus/data /novus/indexwheel ; ");
		}

		executeCommand(executor, command.toString());
	}

	private static void createUsers(PrintStream executor) throws InterruptedException {
		StringBuilder command = new StringBuilder();
		command.append("useradd -d /novus/users/nsa -m -s /bin/bash -c \"Novus Code Deploy Agent\" nsa; usermod -m -d /path/to/new/home/dir userNameHere; usermod -m -d /novus/users/nsa nsa n321v123s; useradd -g novus1 -d /novus/users/novusdev -m -s /bin/bash -c \"Development Process Control User\" novusdev;userdel -r nsa;useradd -g novus1 -d /novus/nib -m -s /bin/bash -c \"Novus NIB User Account\" nib ;");
		executeCommand(executor, command.toString());
	}

}
