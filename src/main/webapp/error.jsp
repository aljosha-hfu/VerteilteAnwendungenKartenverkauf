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
<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Aljosha Vieth">
    <title>Aljoshas Ticketverkauf</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h1>Fehler</h1>
<p><%= errorMessage %></p>
<a href="index.jsp">Zurück zur Hauptseite</a>
</body>
</html>
