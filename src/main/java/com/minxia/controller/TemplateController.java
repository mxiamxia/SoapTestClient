package com.minxia.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.minxia.log.Log;
import com.minxia.model.Form;
import com.minxia.utils.SoapTemplateManager;

@Controller
public class TemplateController extends AController{

	/** 
	 * @author Min
	 * @date Dec 16, 2014
	 * @return void 
	 * @throws 
	 */
	@RequestMapping(value = "saveTemp.mx", method = RequestMethod.POST)
	public String saveTemp(HttpServletRequest request,HttpServletResponse response) 
	{
		String msg = null;
		CopyOnWriteArrayList<Form> list = null;
		boolean isSuc = true;
		boolean isExist = false;
		String key = request.getParameter("type");
		Form form = fillForm(request);
		list = map.get(key);
		if(list != null) {
			for(int ii=0;ii<list.size();ii++)
			{
				if(form.getName().equalsIgnoreCase(((CopyOnWriteArrayList<Form>)list).get(ii).getName())){
					isExist = true;
					((CopyOnWriteArrayList<Form>)list).set(ii, form);
					break;
				}
			}
		}
		else {
			list = new CopyOnWriteArrayList<Form> ();
		}
		if(!isExist){
			list.add(form);
		}
		try {
			map.put(key, list);
			SoapTemplateManager.writeFile(map);
		} catch (Exception e) {
			msg = "failed to save the template file" + e.getMessage();
			isSuc = false;
			Log.out(e);
		}
		if(isSuc){
			msg = "save template file successfully";
		}
		jw.writeJsonOutput(isSuc, response, msg, -1);
		return null;
	}
	
	@RequestMapping(value = "delTemp.mx", method = RequestMethod.POST)
	public String deleteTemp(HttpServletRequest request,HttpServletResponse response) 
	{
		String msg = null;
		CopyOnWriteArrayList<Form> list = null;
		boolean isSuc = true;
		boolean isExist = false;
		String key = request.getParameter("type");
		Form form = fillForm(request);
		list = map.get(key);
		if(list != null) {
			for(int ii=0;ii<list.size();ii++)
			{
				if(form.getName().equalsIgnoreCase(((CopyOnWriteArrayList<Form>)list).get(ii).getName())){
					isExist = true;
					((CopyOnWriteArrayList<Form>)list).remove(((CopyOnWriteArrayList<Form>)list).get(ii));
					break;
				}
			}
			if(!isExist){
				msg = "This template is not exist for deleting";
			}
			else {
				try {
					SoapTemplateManager.writeFile(map);
				} catch (Exception e) {
					msg = "failed to delete the template file" + e.getMessage();
					isSuc = false;
					Log.out(e);
				}
			}
		}
		else {
			msg = "This template is not exist for deleting";
		}

		if(isSuc){
			msg = "delete template file successfully";
		}
		jw.writeJsonOutput(isSuc, response, msg, -1);
		return null;
	}

	@RequestMapping(value = "fill/{name}.mx", method = RequestMethod.GET)
	public String displaytemp(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response) {
		boolean found = false;
		String msg = null;
		Form form = new Form();
		CopyOnWriteArrayList<Form> list = null;
		String key = request.getParameter("type");
		list = map.get(key);
		if(list != null)
		{
			name = name.replace("@", " ");
			for(Form f : list){
				if(name.equalsIgnoreCase(f.getName())){
					form = f;
					found = true;
					break;
				}
			}
		}
		if(found){
			try {
				String output = mapper.writeValueAsString(form);
				jw.writeJsonOutput(true, response, output, -1);
			} catch (IOException e) {
				msg = "error";
				found = false;
				e.printStackTrace();
			}
		}
		return null;
	}
	
	

}
