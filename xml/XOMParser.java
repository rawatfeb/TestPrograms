package xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Nodes;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class XOMParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws ValidityException, ParsingException, IOException {
		System.out.println("Started....");
		XOMParser p = new XOMParser();

		Builder builder = new Builder();
		InputStream ins = new FileInputStream(new File("D:/Rawat/Mars_Workspace/TestPrograms/student_input.xml"));

		Document doc = builder.build(ins);

		p.traverse(doc);
		p.applyXpath(doc);
	}

	public void traverse(Document doc) {
		Element root = doc.getRootElement();
		System.out.println("Root Node : " + root.getLocalName());

		// Get children
		Elements students = root.getChildElements();
		Element nameChild = null;
		for (int i = 0; i < students.size(); i++) {
			System.out.println(" Child : " + students.get(i).getLocalName());

			// Get first child with tag name as 'name'
			nameChild = students.get(i).getFirstChildElement("name");
			if (nameChild != null) {
				System.out.println("  Name : " + nameChild.getValue());
			}
		}
	}

	private void applyXpath(Document doc) {
		Element root = doc.getRootElement();
		Nodes result = root.query("/students/student[name[@rel=\"second\"]]/@href");
		System.out.println(result.get(0).getValue());
		
		
		result = root.query("/students/student/name");
		System.out.println(result.size());
		System.out.println(result.get(0).getValue());
	}

}
