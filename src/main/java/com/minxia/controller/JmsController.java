package com.minxia.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.minxia.execute.JmsExecutor;
import com.minxia.jms.MQJMSClient;
import com.minxia.log.Log;
import com.minxia.model.Form;
import com.minxia.model.JmsForm;
import com.minxia.utils.SoapTemplateManager;
@Controller
public class JmsController extends AController{
	
	@Autowired
	MQJMSClient mqJmsClient;
	public void setMqJmsClient(MQJMSClient mqJmsClient) {
		this.mqJmsClient = mqJmsClient;
	}
	
	@RequestMapping(value = "jms.mx", method = RequestMethod.GET)
	public String loadJmsPage(Model m) 
	{
		CopyOnWriteArrayList<Form> list = null;
		if(SoapTemplateManager.isTempatesExsit())
		{
			try {
				Iterator<Entry<String, CopyOnWriteArrayList<Form>>> it = map.entrySet().iterator();
				while(it.hasNext())
				{
					Entry<String, CopyOnWriteArrayList<Form>> entry = it.next();
					if("jms".equalsIgnoreCase(entry.getKey()))
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
				Log.out(msg, e);
			}
		}
		return "jms";
	}
	
	@RequestMapping(value = "sendJms.mx", method = RequestMethod.POST)
	public String sendSoap(HttpServletRequest request,HttpServletResponse response) {
		String msg = null;
		Form form = fillForm(request);
		executor = new JmsExecutor((JmsForm)form);
		long start = System.currentTimeMillis();
		executor.Execute();
		long cost = System.currentTimeMillis() - start;
		msg = form.getOutput();
		jw.writeJsonOutput(true, response, msg, cost);
		return null;
	}
	
	@RequestMapping(value = "mq.mx", method = RequestMethod.GET)
	public String loadMqPage() 
	{
		return "mqjms";
	}
	
	@RequestMapping(value = "browserJms.mx", method = RequestMethod.GET)
	public String browserMessages(HttpServletRequest request,HttpServletResponse response, Model m) {
		List messages = new ArrayList();
		String JNDI = request.getParameter("jndi");
		try {
			messages = mqJmsClient.browserQMessages(JNDI);
			m.addAttribute("msgList", messages);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			Log.out(e);
		}
		return "mqjms";
	}

}
