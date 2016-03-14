/*package runtime;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.webtool.ros.RunOnSwat;

*//**
 * Servlet implementation class RunOnSwatBoxServlet
 *//*
public class RunOnSwatBoxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	*//**
	 * @see HttpServlet#HttpServlet()
	 *//*
	public RunOnSwatBoxServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	*//**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *//*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	*//**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 *//*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		RunOnSwat runOnSwat = new RunOnSwat();
		request.setAttribute("NovusReleases", runOnSwat.getNovusDirs());
		System.out.println("hello i am from RunOnSwatBoxServlet");
		if (!ServletFileUpload.isMultipartContent(request)) {
			request.getRequestDispatcher("jsp/runOnSwatBox.jsp").forward(request, response);
			return;
		}
	
		try{
			runOnSwat.checkAllRequiredDirectoriesExist();
			runOnSwat.saveAndRun(request, response);
		}catch(Exception t){
			request.setAttribute("msg",t.getMessage());
			System.out.println(request.getAttribute("msg"));
		}
		request.getRequestDispatcher("jsp/runOnSwatBox.jsp").forward(request, response);
	}
}
*/