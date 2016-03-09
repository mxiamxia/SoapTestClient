<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
<title>HTTP Client</title>

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
	
	function label(data) {
		var param = data.replace(/ /g, "@");
		var input = {
				"type" : "jms"
			};
		
		$.ajax({
			url : "fill/" + param + ".mx",
			dataType : 'json',
			data : input,
			type : 'get',
			async : true,
			success : function(data) {
				if (data.isSuc) {
				$('#name').val(data.msg.name);
				$('#state').val(data.msg.state);
				$('#dest').val(data.msg.destination);
				$('#trx').val(data.msg.trx);
				$('#input').val(data.msg.input);
				$("#costLabel").hide();
				$("#cost").hide();
				$("#outputLabel").hide();
				$("#output").hide();
				}
			}
		});
	}

	$(document).ready(function() {

		$('#send').click(function() {
			var dest = $('#dest').val();
			var trx = $('#trx').val();
			var state = $('#state').val();
			var input = $('#input').val();
			
			var param = {
				"dest" : dest,
				"trx" : trx,
				"state" : state,
				"input" : input,
				"type" : "jms"
			};

			/* alert(JSON.stringify(param));  */
			$.ajax({
				url : 'sendJms.mx',
				type : 'POST',
				timeout: 120000,
				data : param,
				dataType : 'json',
				beforeSend:function() { 
					$("<div class=\"datagrid-mask-msg\"><img src='images/waiting.gif' style='position:absolute; width:80px; height:60px;' id='img_loding'/></div>").appendTo("body").css({position:'absolute', left:'50%', top:'50%'});
					}, 
				complete:function(data) { 
					$('.datagrid-mask-msg').remove(); 
					}, 
				success : function(data) {
					if (data.isSuc) {
						$("#outputLabel").show();
						$("#output").show();
						$("#costLabel").show();
						$("#cost").show();
						$("#outputLabel").html("Output");
						$("#output").html(data.msg);
						$("#costLabel").html("Cost");
						$("#cost").html(data.cost +" ms");
					} else {
						$("#outputLabel").html("Output");
						$("#output").html("HTTP Request Error");
					}
				},
				error : function(error) {
					alert('Error occurs');
					$("#outputLabel").show();
					$("#output").show();
					$("#outputLabel").html("Output");
					$("#output").html("Soap Request Error");
				}
			});

			return false;
		});

		$('#save').click(function() {
			var name = $('#name').val();
			if (!name) {
				alert('Please give a template name!');
				return false;
			}
			
			var dest = $('#dest').val();
			var trx = $('#trx').val();
			var state = $('#state').val();
			var input = $('#input').val();
			var param = {
				"name" : name,
				"dest" : dest,
				"trx" : trx,
				"state" : state,
				"input" : input,
				"type" : "jms"
			};

			$.ajax({
				url : "saveTemp.mx",
				type : "POST",
				data : param,
				dataType : "json",
				success : function(data) {
					if (data.isSuc) {
						alert('Saved request tempaltes');
					} else {
						if(data.msg){
							alert(data.msg);				
						}else{
							alert('Failed to save request tempaltes');
						}
					}
				},
				error : function(error) {
					alert('Error occurs');
				}
			});

			return false;
		});
		
		$('#delete').click(function() {
			var name = $('#name').val();
			if (!name) {
				alert('Please give a template name!');
				return false;
			}
			
			var dest = $('#dest').val();
			var trx = $('#trx').val();
			var state = $('#state').val();
			var input = $('#input').val();
			var param = {
				"name" : name,
				"dest" : dest,
				"trx" : trx,
				"state" : state,
				"input" : input,
				"type" : "jms"
			};

			$.ajax({
				url : "delTemp.mx",
				type : "POST",
				data : param,
				dataType : "json",
				success : function(data) {
					if (data.isSuc) {
						alert('Delete request tempaltes');
					} else {
						if(data.msg){
							alert(data.msg);				
						}else{
							alert('Failed to delete request tempaltes');
						}
					}
				},
				error : function(error) {
					alert('Error occurs');
				}
			});

			return false;
		});

		$('#reset').click(function() {
			$('#dest').val();
			$('#trx').val();
			$('#state').val();
			$('#input').val();
			$("#outputLabel").hide();
			$("#output").hide();
			$("#costLabel").hide();
			$("#cost").hide();
		});

	});
</script>

</head>
<body>
	<jsp:include page="nav.jsp?index=jms"></jsp:include>
    <h1>JMS Test Client</h1>
    <div class="bs-example">

        <form class="form-horizontal" onsubmit='return sendSoap()'>
        	<div class="form-group">
                <label class="control-label col-xs-3" for="url">Template Name:</label>
                <div class="col-xs-9">
                    <input type="text" name="name" class="form-control" id="name" placeholder="Template Name"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-xs-3" for="url">Destination:</label>
                <div class="col-xs-9">
                    <input type="text" name="dest" class="form-control" id="dest" placeholder="wfac_se_br1"/>
                </div>
            </div>
            
            <div class="form-group">
                <label class="control-label col-xs-3" for="url">Transaction:</label>
                <div class="col-xs-9">
                    <input type="text" name="trx" class="form-control" id="trx" placeholder="Transaction"/>
                </div>
            </div>
            
            <div class="form-group">
                <label class="control-label col-xs-3" for="url">State:</label>
                <div class="col-xs-9">
                    <input type="text" name="state" class="form-control" id="state" placeholder="state"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-xs-3" for="input">WFAC Input</label>
                <div class="col-xs-9">
                    <textarea rows="6" name="input" class="form-control" id="input" placeholder="WFAC Input"></textarea>
                </div>
            </div>

			<div class="form-group">
				<label class="control-label col-xs-3" for="Select Templates" id="selectTemp">Select Saved Templates</label>
				<div class="dropdown col-xs-9">
					<button class="btn btn-default dropdown-toggle" type="button"
						id="dropdownMenu1" data-toggle="dropdown">JMS Templates<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
						<c:forEach items="${tempList}" var="tmplst">
							<li role="presentation"><a role="menuitem" tabindex="-1" onClick="label('${tmplst.name}')">${tmplst.name}</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>

			<div class="form-group">
                <label class="control-label col-xs-3" for="cost" id="costLabel"></label>
                <div class="col-xs-9" id="cost">
                    <!-- <textarea rows="6" name="output" class="form-control" id="output" style="display:none;"></textarea> -->
                </div>
            </div>

			<div class="form-group">
                <label class="control-label col-xs-3" for="output" id="outputLabel"></label>
                <div class="col-xs-9" id="output">
                    <!-- <textarea rows="6" name="output" class="form-control" id="output" style="display:none;"></textarea> -->
                </div>
            </div>

            <br>
            <div class="form-group">
                <div class="col-xs-offset-3 col-xs-9">
                    <input id="send" type="submit" class="btn btn-primary" value="Submit">
                    <input id="reset" type="reset" class="btn btn-default" value="Reset">
                    <input id="save" type="button" class="btn btn-default" value="Save">
                    <input id="delete" type="button" class="btn btn-default" value="Delete">
                </div>
            </div>
        </form>
    </div>
	<div class="footer">
		<p class="text-muted">
			This tool is only distributed by <strong>Open Source</strong> purpose.
		</p>
		<p class="text-muted">
			Copyright &copy; 2014 Min Xia &mdash; <a
				href="http://www.minxia.net/" title="My Site">Min's blog</a>
		</p>
	</div>
</body>
</html>