
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class TextToPdf {

	public static void main(String[] args) {
		try {
			OutputStream pdfFile = new FileOutputStream(new File("C:\\Rawat\\qt\\Test.pdf"));

			Document document = new Document();
			document.addAuthor("Gaurav Rawat");
			PdfWriter pdfWriter = PdfWriter.getInstance(document, pdfFile);
			document.open();
			BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Rawat\\qt\\EclipseNotes.txt")));
			while((br.readLine()!=null)){
			document.add(new Paragraph(br.readLine()));
			System.out.print(" . ");
			}
			pdfWriter.newPage();
			document.add(new Paragraph(new Date().toString()));
			document.close();
			pdfFile.close();
			pdfWriter.close();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
