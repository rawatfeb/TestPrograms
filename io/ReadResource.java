package io;

import java.io.*;
import java.util.*;

public class ReadResource {

	public static List<String> readTextFromJar(String s) {
		InputStream is = null;
		BufferedReader br = null;
		String line;
		ArrayList<String> list = new ArrayList<String>();

		try {
			is = ReadResource.class.getResourceAsStream(s);
			br = new BufferedReader(new InputStreamReader(is));
			while (null != (line = br.readLine())) {
				list.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static void main(String args[]) throws IOException {
		List<String> list = ReadResource.readTextFromJar("error.log");
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}

		list = ReadResource.readTextFromJar("error.log");
		it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}