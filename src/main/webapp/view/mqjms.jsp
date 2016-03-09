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
<script type="text/javascript">
	$(document).ready(function() {
		$('#send').click(function() {
			var queue = $('#queue').val();
			if (!queue) {
				alert('Please provide Queue JNDI!');
				return false;
			}

			var param = {
				"jndi" : queue
			};

			$.ajax({
				url : "browserJms.mx",
				type : "GET",
				data : param,
				dataType : "json",
				success : function(data) {
				},
				error : function(error) {
					alert('Error occurs');
				}
			});
			return false;
		});
	})
</script>

</head>
<body>
	<jsp:include page="nav.jsp?index=mq"></jsp:include>
	<h1>MQ Messages Browser</h1>
	<div class="bs-example">
		<form class="form-horizontal">
			<div class="form-group">
				<label class="control-label col-xs-3" for="action">Queue
					JNDI:</label>
				<div class="col-xs-9">
					<input type="text" name="queue" class="form-control" id="queue"
						placeholder="QUEUE JNDI" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-xs-3" for="output" id="outputLabel"></label>
				<div class="col-xs-9" id="output">
					<c:choose>
						<c:when test="${empty msgList}">
							<h1>No Message piped in queue</h1>
						</c:when>
						<c:otherwise>
							<h1>All Items</h1>
							<table class="table table-striped">
								<c:forEach var="message" items="${msgList}">
									<tr>
										<td>${message}</td>
									</tr>
								</c:forEach>
							</table>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="form-group">
                <div class="col-xs-offset-3 col-xs-9">
                    <input id="send" type="submit" class="btn btn-primary" value="Submit">
                </div>
            </div>
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