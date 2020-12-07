<%--
  Created by Aljosha Vieth using IntelliJ IDEA Ultimate.
  Date: 04.12.2020
  Time: 13:46
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
<h3><%= errorMessage %></h3>
<h2><a href="index.jsp">Zurück zur Hauptseite</a></h2>
</body>
</html>
