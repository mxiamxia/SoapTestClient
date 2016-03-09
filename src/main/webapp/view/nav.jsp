<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<base href="<%=basePath%>" />

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>
<script language="javascript" type="text/javascript">
  function resizeIframe(obj) {
    obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
  }
</script>

<%
	String curPg = request.getParameter("index").trim();
	String soap = curPg.equals("soap") ? "active" : "";
	String http = curPg.equals("http") ? "active" : "";
	String jms = curPg.equals("jms") ? "active" : "";
	String ivr = curPg.equals("ivr") ? "active" : "";
	String mq = curPg.equals("mq") ? "active" : "";
%>

<nav class="navbar navbar-inverse" role="navigation">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Tools</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="<%=soap%>"><a href="home.mx">Soap</a></li>
        <li class="<%=http%>"><a href="http.mx">HTTP</a></li>
        <li class="<%=jms%>"><a href="jms.mx">JMS</a></li>
        <li class="<%=ivr%>"><a href="ivr.mx">IVR Rule</a></li>
        <li class="<%=mq%>"><a href="mq.mx">MQ Browser</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
