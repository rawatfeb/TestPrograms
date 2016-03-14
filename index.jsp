<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSON Parsing</title>


<script type="text/javascript">
	function GetXmlHttpObject() {
		if (window.XMLHttpRequest) {
			// code for IE7+, Firefox, Chrome, Opera, Safari
			return new XMLHttpRequest();
		}
		if (window.ActiveXObject) {
			// code for IE6, IE5
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
		return null;
	}

	function parseJson() {

		alert("from parse Jsono js");

		var JSONdata;
		var xmlhttp = GetXmlHttpObject();
		xmlhttp.open("GET", "parseJson?time=" + (new Date).getTime()
				+ "&command=parse", true);
		xmlhttp.send();
		var htmlBuilder = "<div>";
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				JSONdata = xmlhttp.responseText; // response from the ajax call	
				if (JSONdata == null || JSONdata == ""
						|| typeof JSONdata == undefined) {
					alert("data is null do something here");
					// in case of returned data is empty then create text field
					document.getElementById("t").outerHTML = "<div>no data returned from server</div>";
					return;
				} else {
					alert(JSONdata);
					var obj = JSON.parse(JSONdata);
					alert("good JSON");
					alert(obj.employees);
					document.getElementById("fake").outerHTML="<input type=\"button\" id=\"fake\" value="+obj.employees+">";

					htmlBuilder = htmlBuilder + JSONdata;
					htmlBuilder = htmlBuilder + "</div>";
					document.getElementById("t").outerHTML = htmlBuilder;
					alert("OOOOOOOOO");
				}
				alert(htmlBuilder);
			}
		};

	}
</script>


</head>
<body>


	<form name="jsonParsing" method="get"
		action="${pageContext.request.contextPath}/parseJson">


		<input type="text" placeholder="type to parse"> <input
			type="submit" value="submit"> <input type="button"
			value="Parse JSON" onclick="parseJson();">

		<div id="t"></div>

		<input type="button" id="fake" value="fake Button">


	</form>

</body>
</html>