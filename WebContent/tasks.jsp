<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="pt">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>To Do List</title>
</head>
<body>
	<h1>Tasks List</h1>
	<form method="post" action="TaskServlet?action=create">
		<label>Task:</label>
		<input name="title" autofocus="autofocus" placeholder="Task to do"  value="${task.title}"/>
		<input type="submit" value="Save">
	</form>
	
	<ul>
		<c:forEach var="tsk" items="${tasks}">
			<c:if test="${!tsk.completed}"> <c:set var="counterTrue" value="${counterTrue + 1}" /> </c:if> 
			<li>
				${tsk.title} - 
				<a href="TaskServlet?action=update&completed=${!tsk.completed}&id=${tsk.id}"> ${tsk.completed} </a>
				&nbsp;
				<a href="TaskServlet?action=delete&id=${tsk.id}"> Del </a>
			</li>
		</c:forEach>
	</ul>
	
	<p>Total: ${fn:length(tasks)} tasks | ${counterTrue} to do</p>
</body>
</html>