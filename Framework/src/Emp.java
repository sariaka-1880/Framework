package model;

import java.util.HashMap;

import etu1880.framework.MethodAnnotation;
import etu1880.framework.ModelView;


public class Emp {
  String nom;
  String prenom;

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

 
  public ModelView Save(){

      HashMap<String,Object> data=new HashMap<String,Object>();

    data.put("test", this.getNom());
    return new ModelView("test.jsp",data);
    
  }



}

