package etu1880.framework.servlet;

import util.Util;
import etu1880.framework.Mapping;
import etu1880.framework.MethodAnnotation;
import etu1880.framework.ModelView;


import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;




public class FrontServlet extends HttpServlet {

HashMap<String,Mapping> MappingUrls = new HashMap<String,Mapping>();
protected Util util;

public void init(PrintWriter out) {

        
    try {

        this.util = new Util();
        this.MappingUrls = new HashMap<>();

        List<String> list=new ArrayList<>();
        
        list=this.util.getconfig("C:/Program Files/Apache Software Foundation/Tomcat 10.0/webapps/test/WEB-INF/config.xml",out);
        for(String liste : list){
            List<Class<?>> allClass = util.getClassesInPackage(liste);  
            Mapping mapping;
            Method[] allMethods;
            for(Class<?> c : allClass) {
                allMethods = c.getMethods();
        
                for(Method m : allMethods) {
                    if(m.isAnnotationPresent(MethodAnnotation.class)) {
                        mapping = new Mapping();
                        mapping.setClassName(c.getName());
                        mapping.setMethod(m.getName());

                        MappingUrls.put(m.getAnnotation(MethodAnnotation.class).url(), mapping);


                        for (String key : MappingUrls.keySet()) {
                            Mapping map = MappingUrls.get(key);
                            out.println(key +":"+ map.getClassName() +"  "+ map.getMethod());
    
                        }

                    }
                }
            }
        }  

    } catch (Exception e) {
        out.println(e.getMessage());
    }
}


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
       
        this.init(out);

        String url = request.getRequestURL().toString();
        String url2=util.getLastPartOfUrl(url);
     
    
        try {
            Mapping mapping = MappingUrls.get(url2);

            out.println("mapping"+mapping);

            if( mapping == null ) {
                throw new Exception("Not Found");
            }

            Class<?> classe = Class.forName(mapping.getClassName());
            out.println("tonga");
            Object o = classe.getDeclaredConstructor().newInstance();
            ModelView mview = (ModelView) o.getClass().getMethod(mapping.getMethod()).invoke(o);


            RequestDispatcher dispatcher = request.getRequestDispatcher(mview.getView());
            dispatcher.forward(request, response);
            out.println(mview.getView());
        

        } catch (Exception e) {
            out.println(e.getMessage());
        }

     

       
    }




}
