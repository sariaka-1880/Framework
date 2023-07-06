package etu1880.framework;

import java.util.HashMap;

public class ModelView {

  String view;
  HashMap<String, Object> session = new HashMap<String, Object>();

  public ModelView(){
    
  }

  HashMap<String,Object> data=new HashMap<String,Object>();

  public ModelView(String view,HashMap<String,Object> data) {
    this.setView(view);
    this.setData(data);
   
  }

  public HashMap<String, Object> getSession() {
    return session;
  }

  public void setSession(HashMap<String, Object> session) {
    this.session = session;
  }

  public HashMap<String, Object> getData() {
    return data;
  }
  public void setData(HashMap<String, Object> data) {
    this.data = data;
  }

  public String getView() {
    return view;
  }

  public void setView(String view) {
    this.view = view;
  }
  
}
