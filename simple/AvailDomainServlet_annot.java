package simple;
/*package com.simple;



import java.io.IOException;

*//**
 * Servlet implementation class AvailDomain
 *//*
@WebServlet("/AvailDomain")
public class AvailDomain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    *//**
     * @see HttpServlet#HttpServlet()
     *//*
    public AvailDomain() {
        super();
        // TODO Auto-generated constructor stub
    }

	*//**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String collection=request.getParameter("collection");
		System.out.println(request.getParameter("timeid"));
		System.out.println(collection);
		String text=null;
		if(null!=collection&&collection.contains("pr")){
		 text="domain=PRLOADB("+collection+")";}else {text="SHLOADB("+collection+")";}
		System.out.println(text);
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(text);
		
	}

	*//**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
*/