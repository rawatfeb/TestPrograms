package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetMIMEType {
public static void main(String[] args) {
	Path source = Paths.get("C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg");
    try {
		System.out.println(Files.probeContentType(source));
	} catch (IOException e) {
		e.printStackTrace();
	}
    // output : image/tiff
	
}
}
