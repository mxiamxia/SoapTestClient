package com.minxia.model;

import java.util.ArrayList;
import java.util.List;

public class FormTest {

	/** 
	 * @author Min
	 * @date Dec 16, 2014
	 * @return void 
	 * @throws 
	 */
	static List<Form> list = new ArrayList<Form>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Form f = new WsForm();
		((WsForm)f).setName("soap");
		((WsForm)f).setAction("test");
		list.add(f);
		
		Form f2 = new HttpForm();
		f2.setName("jsm");
		list.add(f2);
		print(list);
	}
	private static void print(List<Form> list2) {
		// TODO Auto-generated method stub
		for(Form f : list2)
		{
			System.out.println(f.getName());
		}
	}

}
