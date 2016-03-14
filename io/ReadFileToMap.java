package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;

public class ReadFileToMap {
	static Set<String> myTreeSet = new TreeSet<String>();
	static Set<String> myTreeSetReturn = new TreeSet<String>();

	public static void main(String[] args) throws Exception {

		readBeforeFile();
		readReturnFile();

		System.out.println("myTreeSet=" + myTreeSet.size());
		System.out.println("myTreeSetReturn=" + myTreeSetReturn.size());

		myTreeSet.removeAll(myTreeSetReturn);

		System.out.println(myTreeSet);
		// c986fatnovus.westlan.com48897,  c999fuunovus.westlan.com20112,  c999fuunovus.westlan.com22626,  ditter.westlan.com7020,  greer.westlan.com14393,  ns0818-13.westlan.com11965,  ns0824-04.westlan.com7823,  ns0895-12.westlan.com13021,  ns1041-04.westlan.com30985,  ns1079-07.westlan.com332,  ns1106-14.westlan.com4191,  ns1110-13.westlan.com19824,  ns1222-04.westlan.com20075,  ns1247-15.westlan.com64625,  ns1251-09.westlan.com64783,  ns1255-01.westlan.com20052,  ns1284-14.westlan.com64949,  ns1288-14.westlan.com41180,  vandling.westlan.com2683,  wagram.westlan.com11276]

	}

	private static void readReturnFile() throws Exception {
		FileInputStream fin = new FileInputStream("C:\\Users\\U6025719\\Documents\\LogFiles\\jstatdGetVmArgs.log");
		InputStreamReader is = new InputStreamReader(fin);
		BufferedReader br = new BufferedReader(is);
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] stArray = line.split(",");
			for (String string : stArray) {
				myTreeSet.add(string);
			}
		}
		br.close();
		is.close();
		fin.close();
	}

	private static void readBeforeFile() throws Exception {
		FileInputStream fin = new FileInputStream("C:\\Users\\U6025719\\Documents\\LogFiles\\jstatdGetVmArgsReturn.log");
		InputStreamReader is = new InputStreamReader(fin);
		BufferedReader br = new BufferedReader(is);
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] stArray = line.split(",");
			for (String string : stArray) {
				myTreeSetReturn.add(string);
			}
		}
		br.close();
		is.close();
		fin.close();
	}
}
