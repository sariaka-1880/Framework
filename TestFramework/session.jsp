<%@ page import = "java.util.HashMap"%>
<%@ page import = "etu1880.framework.FileUpload"%>
<%@ page import = "jakarta.servlet.http.HttpSession" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "java.util.Map" %>
<%@ page import = "java.util.Map.Entry" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
</head>
<body>
<%
HttpSession zavatra= request.getSession();
HashMap<String, Object> sessionData = (HashMap<String, Object>) zavatra.getAttribute("test");

 for (Map.Entry<String, Object> entry : sessionData.entrySet()) { %>
    <p>Clé : <% out.print( entry.getKey()); %></p>
    <p>Valeur : <% out.print( entry.getValue()); %></p>

<% } %>


</body>
</html>