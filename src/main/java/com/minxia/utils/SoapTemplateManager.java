package com.minxia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.minxia.model.Form;
import com.minxia.model.WsForm;

public class SoapTemplateManager {
	
//	private static String path = "C:/Work/tool_templates12.obj";
	private static String path = "C:/Work/tool_templates.obj";
	private static File file = new File(path);
	
//	private static String path = "/appl/ntelagent/6080/bis/soap_templates.obj";
	
/*	static
	  {
	    String ntel_dir = System.getProperty("NTEL.DIR");
	    String t_path = "bis/tool_templates.obj";
	    if ((ntel_dir != null) && (!(ntel_dir.equalsIgnoreCase("")))) {
	      path = ntel_dir + File.separator + t_path;
	      file = new File(path);
	    }
	  }*/
	/**
	 * @throws Exception  
	 * @author Min
	 * @date Aug 20, 2014
	 * @return void 
	 * @throws 
	 */
	
	 public static synchronized void writeFile(Map<String, CopyOnWriteArrayList<Form>> map) throws Exception {
		 ObjectOutputStream out = null;
	        try {
	            out = new ObjectOutputStream(new FileOutputStream(file));
	            out.writeObject(map);
	        } catch (Exception e) {  
	        	throw e;
	        } finally{
	        	out.close();
	        }
	    }
	     
	    @SuppressWarnings("unchecked")
	    public static Map<String, CopyOnWriteArrayList<Form>> readFile() throws Exception {
	    	Map<String, CopyOnWriteArrayList<Form>> map = null;
	    	ObjectInputStream in = null;
	        try {
	            in = new ObjectInputStream(new FileInputStream(file));
	            map = (Map<String, CopyOnWriteArrayList<Form>>)in.readObject();
	        } catch (Exception e) {
	        	throw e;
	        } finally{
	        	in.close();
	        }
	        return map;
	    }
	    
	    public static boolean isTempatesExsit(){
	    	if(file.exists()){
	    		return true;
	    	}
	    	return false;
	    }
}
