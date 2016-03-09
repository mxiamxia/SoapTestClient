<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Web Service Client</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/sticky-footer.css" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<link href="css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script> -->
<script src="js/fileinput.min.js"></script>

<style type="text/css">
h1 {
	margin: 30px;
	padding: 0 200px 15px 0;
	border-bottom: 1px solid #E5E5E5;
}

.bs-example {
	margin: 10px 90px 0 20px;
}
</style>

</head>
<body>
	<jsp:include page="nav.jsp?index=ivr"></jsp:include>
	<h1>IVR Rule Convertor</h1>
	<div class="bs-example">

		<form class="form-horizontal" method="POST" action="uploadFile.mx"
			enctype="multipart/form-data">
			<label class="control-label">Select Agent.clp to convert</label> <input
				id="input-1a" type="file" name="file" class="file" data-show-preview="false">
		</form>
	</div>
	<div class="footer">
		<p class="text-muted">
			This tool is only distributed by <strong>Open Source</strong>
			purpose.
		</p>
		<p class="text-muted">
			Copyright &copy; 2014 Min Xia &mdash; <a
				href="http://www.minxia.net/" title="My Site">Min's blog</a>
		</p>
	</div>
</body>
</html>