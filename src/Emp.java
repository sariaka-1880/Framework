package model;

import java.util.HashMap;

import etu1880.framework.MethodAnnotation;


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

  @MethodAnnotation(url ="test")
  public void Affichernom(){
    System.out.println("hey");
  
  }



}

