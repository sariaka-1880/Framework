package model;

import java.util.HashMap;

import etu1880.framework.MethodAnnotation;
import etu1880.framework.ModelView;
import etu1880.framework.FileUpload;
import etu1880.framework.Authentification;


public class Emp {

  String nom;
  String prenom;

  FileUpload file;
  
  public FileUpload getFile() {
    return file;
  }
  public void setFile(FileUpload file) {
    this.file = file;
  }

  public String getNom() {
    return nom;
  }
  public void setNom(String nom) {
    this.nom = nom;
  }
  public String getPrenom() {
    return prenom;
  }
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

@MethodAnnotation(url ="/test")

  public ModelView Affichernom(){

    HashMap<String,Object> data=new HashMap<String,Object>();

    data.put("test", this.getNom());
    return new ModelView("test.jsp",data);
  
  }

  @MethodAnnotation(url ="/sprint" , paramName = "Nom,Prenom")

  public ModelView print(String Nom, String Prenom){

    HashMap<String,Object> data=new HashMap<String,Object>();

    this.setNom(Nom);
    this.setPrenom(Prenom);

    data.put("test", this.getNom());
    return new ModelView("test.jsp",data);
    
  }

  @MethodAnnotation(url ="/file", paramName = "File" )
  
  public ModelView file(FileUpload file){

    HashMap<String,Object> data=new HashMap<String,Object>();

    this.setFile(file);
    data.put("test",this.getFile());
    return new ModelView("File.jsp", data);
  }


  @Authentification ( profile= "client")
  @MethodAnnotation(url="/testprofile")

  public ModelView Onlyuser(){

    HashMap<String,Object> data=new HashMap<String,Object>();
    data.put("test","sariaka");
    return new ModelView("session.jsp", data);
  }


}

