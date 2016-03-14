package simple;
import java.io.IOException;


public class RuntimeTest {
public static void main(String...args) throws Exception{
	execTest();
	freeMemoryTest();
}

private static void execTest() throws IOException {
	 String mountablePath="/novus/index/";
	 Process process = Runtime.getRuntime().exec("dir");
	
//	Process process = Runtime.getRuntime().exec("cd "+mountablePath+";df -kh .");
	int data = process.getInputStream().read();
	System.out.println(data);
}

private static void freeMemoryTest(){
	 long freeMemory = Runtime.getRuntime().freeMemory();
	 System.out.println(freeMemory);
}

private static void sshTest() throws Exception {
	/*SshUtils sshUtils = new SshUtils();
	sshUtils.openSshConnection("santafe", "novus", "n0vus!");
	String mountablePath="/novus/indexwheel/client/shared/aranzadi";
	String rData = sshUtils.executeCommand("cd "+mountablePath+";df -kh . | grep :/");
	System.out.println(rData);//Filesystem            Size  Used Avail Use% Mounted onclnt-corp-h0196:/vol/nv_sharedclient_snap/nsindexclientAlv                      4.9T  1.2T  3.7T  25% /novus/index/client/shared
	*/
}


}
