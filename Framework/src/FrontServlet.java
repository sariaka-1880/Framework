package etu1880.framework.servlet;

import util.Util;
import etu1880.framework.Mapping;
import etu1880.framework.MethodAnnotation;
import etu1880.framework.ModelView;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.lang.reflect.*;

import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Emp;



public class FrontServlet extends HttpServlet {

HashMap<String,Mapping> MappingUrls = new HashMap<String,Mapping>();
protected Util util;

public void init(PrintWriter out) {

        
    try {

        this.util = new Util();
        this.MappingUrls = new HashMap<>();

        List<String> list=new ArrayList<>();
        
        list=this.util.getconfig("C:/Program Files/Apache Software Foundation/Tomcat 10.0/webapps/TestFramework/WEB-INF/config.xml",out);
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
            Class<?> classe = Class.forName(mapping.getClassName());
            Object o = classe.getDeclaredConstructor().newInstance();

            out.println("mapping"+mapping);

            if( mapping == null ) {
                throw new Exception("Not Found");
            }

            out.println("tonga");
            
            ModelView mview = (ModelView) o.getClass().getMethod(mapping.getMethod()).invoke(o);

            HashMap<String,Object> datatest=new HashMap<String,Object>();
            
            datatest=mview.getData();
            
            if(datatest==null){

                out.print("null");
            }
            if(datatest!=null){
                for (String key :datatest.keySet()){
                    Object dataObject=datatest.get(key);
                       request.setAttribute(key,dataObject);            
                }
                
            }
            Enumeration<String> parametre= request.getParameterNames();

            while(parametre.hasMoreElements()){

                String param=parametre.nextElement();
                out.println(param);
                Field[] fields = o.getClass().getDeclaredFields();
                Field field=fields[1];
    
                if( field == null){
                    continue;
                }
    
                Object value = null;
                Class<?> parameterType = o.getClass().getDeclaredMethod("set" + param , field.getType()).getParameterTypes()[0];
    
                if (parameterType == String.class) {
                    value = request.getParameter(param);
                } else if (parameterType == int.class || parameterType == Integer.class) {
                    value = Integer.parseInt(request.getParameter(param));
                } else if (parameterType == double.class || parameterType == Double.class) {
                    value = Double.parseDouble(request.getParameter(param));
                } else if (parameterType == boolean.class || parameterType == Boolean.class) {
                    value = Boolean.parseBoolean(request.getParameter(param));
                } else {
            
                    throw new IllegalArgumentException(" géré : " + parameterType.getName());
                }
    
                o.getClass().getDeclaredMethod("set" + param, parameterType).invoke(o, value);
                out.println("tonga " + value);

            request.setAttribute("test",value);
            RequestDispatcher dispatcher = request.getRequestDispatcher(mview.getView());
            dispatcher.forward(request, response);

            out.println(mview.getView());

            }


        } catch (Exception e) {
            try {
                throw new Exception(url2, null);
            } catch (Exception ex) {
                // TODO: handle exception
                ex.printStackTrace(out);
            }
            
            
        }

     

       
    }




}
