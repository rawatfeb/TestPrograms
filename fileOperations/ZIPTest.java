package fileOperations;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.axis.encoding.Base64;

public class ZIPTest {
	public static void main(String... args) throws Throwable {
		// zipInputStreamExample();
		// zipOutputStreamExample();
		// openFile();
		// deflate();
		// inflate();
		// test();
		//UnZip();
		Zip();

		close();
	}

	private static void test() {
		try {
			// Encode a String into bytes
			String inputString = "Pehla nasha Pehla khumaar Naya pyaar hai naya intezaar Kar loon main kya apna haal Aye dil-e-bekaraar Mere dil-e-bekaraar Tu hi bata Pehla nasha Pehla khumaar Udta hi firoon in hawaon mein kahin Ya main jhool jaoon in ghataon mein kahin Udta hi firoon in hawaon mein kahin Ya main jhool jaoon in ghataon mein kahin Ek kar doon aasmaan zameen Kaho yaaron kya karoon kya nahin Pehla nasha Pehla khumaar Naya pyaar hai naya intezaar Kar loon main kya apna haal Aye dil-e-bekaraar Mere dil-e-bekaraar Tu hi bata Pehla nasha Pehla khumaar Usne baat ki kuchh aise dhang se Sapne de gaya vo hazaaron range ke Usne baat ki kuchh aise dhang se Sapne de gaya vo hazaaron range ke Reh jaoon jaise main haar ke Aur choome vo mujhe pyaar se Pehla nasha Pehla khumaar Naya pyaar hai naya intezaar Kar loon main kya apna haal Aye dil-e-bekaraar Mere dil-e-bekaraar";
			byte[] input = inputString.getBytes("UTF-8");

			// Compress the bytes
			byte[] output1 = new byte[input.length];
			Deflater compresser = new Deflater();
			compresser.setInput(input);
			compresser.finish();
			int compressedDataLength = compresser.deflate(output1);
			compresser.end();

			String str = Base64.encode(output1);
			System.out.println("Deflated String:" + str);

			byte[] output2 = Base64.decode(str);

			// Decompress the bytes
			Inflater decompresser = new Inflater();
			decompresser.setInput(output2);
			byte[] result = str.getBytes();
			int resultLength = decompresser.inflate(result);
			decompresser.end();

			// Decode the bytes into a String
			String outputString = new String(result, 0, resultLength, "UTF-8");
			System.out.println("Deflated String:" + outputString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static byte[] decompressBytes(byte[] inBytes) {
		byte[] outBytes = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		boolean exceptionOccured = false;
		byte[] buffer = new byte[inBytes.length];
		int bufferSize = 0;
		InflaterInputStream iis = null;
		try {
			iis = new InflaterInputStream(new ByteArrayInputStream(inBytes));
			while (((bufferSize = iis.read(buffer, 0, buffer.length)) != -1)) {
				out.write(buffer, 0, bufferSize);
			}
		} catch (Exception e) {
			if (!(e instanceof EOFException)) {
				e.printStackTrace();
				exceptionOccured = true;
			}
		} finally {
			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
				}
			}
			outBytes = exceptionOccured ? inBytes : out.toByteArray();
		}
		return outBytes;
	}

	public static byte[] decompressBytes(byte[] inBytes, int expectedSize) throws IOException {
		byte[] outBytes = new byte[expectedSize];
		Inflater inflater = new Inflater();
		inflater.setInput(inBytes);
		int inflatedBytes = 0;
		try {
			inflatedBytes = inflater.inflate(outBytes);
			inflater.end();
		} catch (java.util.zip.DataFormatException dfe) {

			return null;
		}
		// The decompressed bytes should match what the database said
		return outBytes;
	}

	private static void inflate() throws Exception {
		String name = "ZipTest.properties";
		File file = new File(name);
		FileInputStream fis = new FileInputStream(file);

		Inflater inflater = new Inflater();
		byte[] bytes = null;
		while (fis.read(bytes) != -1) {
			inflater.inflate(bytes);
		}

		// .

		inflater.end();
	}

	private static void deflate() throws Exception {
		// String name="ZipTest.zip";
		String name = "ZipTest.properties";
		File file = new File(name);
		FileInputStream fis = new FileInputStream(file);
		byte[] input = new byte[1024];
		Deflater deflater = new Deflater();
		System.out.println(new String(input));
		byte[] output = new byte[1024];

		String deflatedName = "ZipTest.defl";
		File file2 = new File(deflatedName);
		file2.createNewFile();
		FileOutputStream fos = new FileOutputStream(file2);

		while (fis.read(input) != -1) {
			deflater.setInput(input);
			deflater.deflate(output);
			fos.write(output);
			System.out.println(new String(output));
		}

		deflater.end();

	}

	/**
	 * Compresses a zlib compressed file.
	 */
	public static void compressFile(File raw, File compressed) throws IOException {
		InputStream in = new FileInputStream(raw);
		OutputStream out = new DeflaterOutputStream(new FileOutputStream(compressed));
		shovelInToOut(in, out);
		in.close();
		out.close();
	}

	/**
	 * Decompresses a zlib compressed file.
	 */
	public static void decompressFile(File compressed, File raw) throws IOException {
		InputStream in = new InflaterInputStream(new FileInputStream(compressed));
		OutputStream out = new FileOutputStream(raw);
		shovelInToOut(in, out);
		in.close();
		out.close();
	}

	private static void shovelInToOut(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1000];
		int len;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
	}

	private static void close() {

	}

	private static void openFile() throws Exception {
		// String name="ZipTest.zip";
		String name = "zpt.zip";
		ZipFile zf = new ZipFile(name);
		// Enumerate the entries
		Enumeration zip_entries = zf.entries();
		String last_name = null;
		while (zip_entries.hasMoreElements()) {
			ZipEntry ze = (ZipEntry) zip_entries.nextElement();
			System.out.println("Entry " + ze.getName());
			last_name = ze.getName();
		}
		zf.close();
	}

	private static void zipInputStreamExample() throws Exception {
		// String name = "zpt.zip";
		String name = "fscontext.jar.zip";
		FileInputStream fis = new FileInputStream(name);
		ZipInputStream zis = new ZipInputStream(fis);
		ZipEntry ze = zis.getNextEntry();
		if (ze != null) {
			System.out.println(ze.getName());
		} else {
			System.out.println("null...");
		}
		DataInputStream dis = new DataInputStream(zis);
		int i = dis.readInt();
		System.out.println("File contains " + i);
		zis.closeEntry();
		zis.close();

	}

	private static void zipOutputStreamExample() throws Exception {
		String name = "out.zip";
		File file = new File(name);
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		ZipOutputStream zos = new ZipOutputStream(fos);
		ZipEntry ze = new ZipEntry("test.txt");
		zos.putNextEntry(ze);
		// Use it as output
		DataOutputStream dos = new DataOutputStream(zos);
		dos.writeInt(1);
		zos.closeEntry();
		// Put another entry or close it
		zos.close();

	}

	public static void UnZip() {
		final int BUFFER = 2048;
		try {
			BufferedOutputStream dest = null;
			String name = "fscontext.jar.zip";
			FileInputStream fis = new FileInputStream(name);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			System.out.println(fis.available());
			ZipEntry entry;
			while ((entry = zis.getNextEntry()) != null) {
				System.out.println("Extracting: " + entry);
				int count;
				byte data[] = new byte[BUFFER];
				// write the files to the disk
				FileOutputStream fos = new FileOutputStream(entry.getName());
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = zis.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
			}
			zis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Zip() {
		final int BUFFER = 2048;
		try {
			BufferedInputStream origin = null;
			FileOutputStream destinationZipFile = new FileOutputStream("C:\\Rawat\\buffer\\putty.zip");
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(destinationZipFile));
			// out.setMethod(ZipOutputStream.DEFLATED);
			byte data[] = new byte[BUFFER];
			// get a list of files from  directory
			File f = new File("C:\\Rawat\\buffer");   // source taht to be zipped
			//String files[] = f.list();
			 File[] files = f.listFiles();

			for (int i = 0; i < files.length; i++) {
				System.out.println("Adding: " + files[i]);
				FileInputStream fi = new FileInputStream(files[i].getAbsolutePath());
				origin = new BufferedInputStream(fi, BUFFER);
				//ZipEntry entry = new ZipEntry(files[i].getAbsolutePath());
				ZipEntry entry = new ZipEntry(files[i].getName());
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
