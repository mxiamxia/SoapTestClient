package com.minxia.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import com.minxia.model.WsForm;

public class SoapTemplateManagerTest {

	private static File file = new File("src/main/resources/soap_templates.obj");
	/** 
	 * @author Min
	 * @date Aug 20, 2014
	 * @return void 
	 * @throws 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WsForm form = new WsForm();
		Collection<WsForm> formList = new CopyOnWriteArrayList<WsForm>();
		form.setUrl("www.test.com");
		form.setAction("test");		
		formList.add(form);
		
//		writeFile(formList);
		
		Collection<WsForm> rmap = readFile();
		System.out.println(((CopyOnWriteArrayList<WsForm>)rmap).get(0).toString());

	}
	
	 public static void writeFile(Collection<WsForm> list) {
	        try {
	            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
	            out.writeObject(list);
	            out.close();
	        } catch (FileNotFoundException e) {            
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	     
	    @SuppressWarnings("unchecked")
	    public static Collection<WsForm> readFile() {
	    	Collection<WsForm> list = null;
	        try {
	            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
	            list = (Collection<WsForm>)in.readObject();
	            in.close();            
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }

}
