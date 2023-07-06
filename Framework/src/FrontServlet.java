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
            if( mapping == null ) {
                throw new Exception("Not Found");
            }

            Class<?> classe = Class.forName(mapping.getClassName());
            Object o = classe.getDeclaredConstructor().newInstance();

            out.println("mapping"+mapping);

            out.println("tonga1");

            String className = mapping.getClassName();
            String methodName = mapping.getMethod();
            
            Util util=new Util();

            try {
                
                Method method = util.getMethodByclassName(className, methodName);

                if (method.isAnnotationPresent(MethodAnnotation.class)) {
                    MethodAnnotation annotation = method.getAnnotation(MethodAnnotation.class);
                    String paramName = annotation.paramName(); 
                    String[] paramNames = paramName.split(","); 
                    Class<?>[] parameterTypes = method.getParameterTypes();

                    out.println("tonga2");

                    Object[] arguments = new Object[paramNames.length];

                    if (paramNames.length == parameterTypes.length) {

                        out.println("tonga3");

                        for (int i = 0; i < paramNames.length; i++) {
                            String paramNom = paramNames[i].trim();
                            Class<?> parameterType = parameterTypes[i];

                            if (parameterType == String.class) {
                                arguments[i] = request.getParameter(paramNom);
                            } else if (parameterType == int.class || parameterType == Integer.class) {
                                arguments[i] = Integer.parseInt(request.getParameter(paramNom));
                            } else if (parameterType == double.class || parameterType == Double.class) {
                                arguments[i] = Double.parseDouble(request.getParameter(paramNom));
                            } else if (parameterType == boolean.class || parameterType == Boolean.class) {
                                arguments[i]= Boolean.parseBoolean(request.getParameter(paramNom));
                            } else {
                        
                                throw new IllegalArgumentException(" géré : " + parameterType.getName());
                            }
                            out.println("tonga4");

                            ModelView mview = (ModelView) o.getClass().getMethod(mapping.getMethod(),parameterTypes).invoke(o,arguments);

                            out.println("tonga5 " + arguments);

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
                
                            request.setAttribute("test",arguments[i]);
                            RequestDispatcher dispatcher = request.getRequestDispatcher(mview.getView());
                            dispatcher.forward(request, response);
                        }       
                    }
                }
            }catch (Exception e) {
                    throw e;
            }

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
                e.printStackTrace(out);
         
        }
       
    }

}
