<%--
  Created by IntelliJ IDEA.
  User: Aljosha
  Date: 04.12.2020
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    final String errorMessage = (String) request.getAttribute ("errorMessage");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p><%= errorMessage %></p>
</body>
</html>
