<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
</head>
<body>
<h3>methode set attribut</h3>
  <form action="http://localhost:8080/TestFramework/test" method="post">

    <input type="text" name="Nom">
    <input type="text" name="Prenom" id="">
    <input type="submit" value="valider" id="">
    
  </form>

<h3>methode set parameter</h3>
  <form action="http://localhost:8080/TestFramework/sprint" method="post">

    <input type="text" name="Nom">
    <input type="text" name="Prenom" id="">
    <input type="submit" value="valider" id="">
    
  </form>

  <h3>file upload </h3>
  <form action="http://localhost:8080/TestFramework/file" method="post" enctype="multipart/form-data" >
    <input type="file" name="File">
    <input type="submit" value="valider" id="">
  </form>

    <h3>connection</h3>
  
  <form action="http://localhost:8080/TestFramework/testprofile" method="post">
    <input type="test" name="Nom">
    <input type="submit" value="valider" id="">
  </form>

</body>
</html>