package com.minxia.controller;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.minxia.execute.HttpExecutor;
import com.minxia.model.Form;
import com.minxia.model.HttpForm;
import com.minxia.utils.SoapTemplateManager;

@Controller
public class HttpController extends AController{
	
	@RequestMapping(value = "http.mx", method = RequestMethod.GET)
	public String loadJmsPage(Model m) 
	{
		CopyOnWriteArrayList<Form> list = null;
		if (SoapTemplateManager.isTempatesExsit()) 
		{
			try 
			{
				list = map.get("http");
				if (list != null) 
				{
					m.addAttribute("tempList", list);
				}
			} 
			catch (Exception e) 
			{
				String msg = "failed to read templates file";
				m.addAttribute("err", msg);
				e.printStackTrace();
			}
		}
		return "http";
	}
	
	@RequestMapping(value = "sendHttp.mx", method = RequestMethod.POST)
	public String sendSoap(HttpServletRequest request,HttpServletResponse response) {
		String msg;
		Form form = fillForm(request);
		executor = new HttpExecutor((HttpForm)form);
		long start = System.currentTimeMillis();
		executor.Execute();
		long cost = System.currentTimeMillis() - start;
		msg = form.getOutput();
		jw.writeJsonOutput(true, response, msg, cost);
		return null;
	}
	
}
