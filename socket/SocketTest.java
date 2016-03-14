package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SocketTest {
	public static void main(String[] args) {
		run();
	}

	public static void run() {
		int serverPort = 8989;
		try (Socket socket = new Socket(InetAddress.getLocalHost(), serverPort)) {
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(outputStream);
			sendLogRequests(writer, socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private static void sendLogRequests(PrintWriter writer, InputStream inputStream) throws IOException {
		for (int i = 0; i < 4; i++) {
			String clientName = "hello";
			writer.println(clientName + " - Log request: " + i);
			writer.flush();

			byte[] data = new byte[1024];
			int read = inputStream.read(data, 0, data.length);
			if (read == 0) {
				System.out.println("Read zero bytes");
			} else {
				System.out.println(new String(data, 0, read));
			}

			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
