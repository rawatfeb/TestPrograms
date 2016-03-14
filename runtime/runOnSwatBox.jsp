<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.List"%>
<html>
<head>
<%
	String strCtx = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome To Swat Tools</title>
</head>

<style type="text/css">
.table {
	background-color: #F6FFF5;
}

h3 {
	color: red;
}
</style>
<body>

	<form action="<%=strCtx%>/run" method="post"
		enctype="multipart/form-data">

		<h1>Welcome to Run On Swat Box</h1>
		<a href="<%=strCtx%>/index.jsp">Home</a>
		<hr />
		<table class="table">
			<tr>
				<td><label>Novus Releases</label></td>
				<td><label><select name="NovusReleases">
							<%
								List<String> NovusReleases = (List) request.getAttribute("NovusReleases");
								for (String NovusRelease : NovusReleases) {
									out.println("<option value=\"" + NovusRelease + "\">" + NovusRelease + "</option>");
								}
							%>
					</select></label></td>
			</tr>
			<tr>
				<td><label>Program File</label></td>
				<td><input type="file" name="programFile"></input></td>
			<tr>
				<td><label>Custom Jar (Optional)</label></td>
				<td><input type="file" name="CustomJar"></input></td>
			<tr>
				<td><input type="submit" value="Run"></input></td>
			</tr>

		</table>
		<h3><%=(null != request.getAttribute("msg") ? request.getAttribute("msg") : "")%></h3>
	</form>
</body>
</html>