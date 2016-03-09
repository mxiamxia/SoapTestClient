package com.minxia.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.minxia.execute.Executor;
import com.minxia.execute.JmsExecutor;
import com.minxia.execute.SoapExecutor;
import com.minxia.model.Form;
import com.minxia.model.JmsForm;
import com.minxia.model.WsForm;
import com.minxia.utils.JsonWriter;
import com.minxia.utils.SoapTemplateManager;

@Controller
public class SoapController extends AController{
	
	@RequestMapping(value = "home.mx", method = RequestMethod.GET)
	public String loadHomePage(Model m) 
	{
		CopyOnWriteArrayList<Form> list = null;
		if(SoapTemplateManager.isTempatesExsit())
		{
			try {
				Iterator<Entry<String, CopyOnWriteArrayList<Form>>> it = map.entrySet().iterator();
				while(it.hasNext())
				{
					Entry<String, CopyOnWriteArrayList<Form>> entry = it.next();
					if("soap".equalsIgnoreCase(entry.getKey()))
					{
						list = (CopyOnWriteArrayList<Form>) entry.getValue();
						break;
					}
				}
				if(list != null)
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
		return "main";
	}
	
	@RequestMapping(value = "sendSoap.mx", method = RequestMethod.POST)
	public String sendSoap(HttpServletRequest request,HttpServletResponse response) {
		String msg;
		Form form = fillForm(request);
		executor = new SoapExecutor((WsForm)form);
		long start = System.currentTimeMillis();
		executor.Execute();
		long cost = System.currentTimeMillis() - start;
		msg = form.getOutput();
		jw.writeJsonOutput(true, response, msg, cost);
		return null;
	}
}
