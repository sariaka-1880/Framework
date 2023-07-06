<%@ page import = "java.util.HashMap"%>
<%@ page import = "etu1880.framework.FileUpload"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
</head>
<body>
  Bienvenue
  <%
   FileUpload datatest = (FileUpload) request.getAttribute("test");
    byte[] fileBytes = datatest.getFile();
    out.print(fileBytes);
  %>
</body>
</html>