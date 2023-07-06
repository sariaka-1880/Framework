package util;

import java.util.List;
import java.io.File;

import etu1880.framework.Mapping;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.DocumentCSS;

import java.util.Objects;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;

import java.util.HashMap;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.Collections;
import java.util.Enumeration;


public class Util {
  

  public List<String> getconfig(String file,PrintWriter out)throws Exception {

    List<String> valiny= new ArrayList<>();
    File fichier=new File(file);
    DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
    Document document=documentBuilder.parse(fichier);
    Element racine = document.getDocumentElement();
    out.println(racine.getNodeName());
    System.out.println(racine.getNodeName());
    NodeList nodeList = racine.getChildNodes();

    for(int i=0; i<nodeList.getLength();i++){
      Node node=nodeList.item(i);
   
      if(node.getNodeType()==Node.ELEMENT_NODE){
        Element element=(Element) node;
        System.out.println(element.getTagName());
        System.out.println(element.getTextContent());
        valiny.add(element.getTextContent());
      }
    }
    return valiny;

  }

public static List<Class<?>> getClassesInPackage(String packageName) {
  List<Class<?>> classes = new ArrayList<>();
  String path = packageName.replace(".", "/");
  try {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      Enumeration<URL> resources = classLoader.getResources(path);
      while (resources.hasMoreElements()) {
          URL resource = resources.nextElement();
          String protocol = resource.getProtocol();
          if (protocol.equals("jar")) {
              JarURLConnection connection = (JarURLConnection) resource.openConnection();
              JarFile jarFile = connection.getJarFile();
              for (JarEntry entry : Collections.list(jarFile.entries())) {
                  String name = entry.getName().replace("/", ".");
                  if (name.startsWith(packageName) && name.endsWith(".class")) {
                      String className = name.substring(0, name.length() - 6);
                      classes.add(Class.forName(className));
                  }
              }
          } else if (protocol.equals("file")) {
              File directory = new File(URLDecoder.decode(resource.getFile(), "UTF-8"));
              if (directory.exists()) {
                  for (File file : directory.listFiles()) {
                      if (file.isFile() && file.getName().endsWith(".class")) {
                          String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                          classes.add(Class.forName(className));
                      }
                  }
              }
          }
      }
  } catch (Exception e) {
      e.printStackTrace();
  }
  return classes;
}


public String getLastPartOfUrl(String url) {

  int lastSlashIndex = url.lastIndexOf("/");
  return url.substring(lastSlashIndex);
  
}

public Method getMethodByclassName(String Nomclasse , String method)throws NoSuchMethodError{
  Method valiny=null;
  try {

    Class<?> clazz = Class.forName(Nomclasse);
    Object o = clazz.getDeclaredConstructor().newInstance();
    Method[] allMethods = o.getClass().getDeclaredMethods();
    for(Method m : allMethods){
      if(m.getName().equals(method)) {
        valiny = m;
        break;
      }
    }
    
  } catch (Exception e) {
    // TODO: handle exception
  }
  return valiny;
}

}

