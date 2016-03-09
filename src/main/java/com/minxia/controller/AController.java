package com.minxia.controller;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;

import com.minxia.execute.Executor;
import com.minxia.log.Log;
import com.minxia.model.Form;
import com.minxia.model.HttpForm;
import com.minxia.model.JmsForm;
import com.minxia.model.WsForm;
import com.minxia.utils.JsonWriter;
import com.minxia.utils.SoapTemplateManager;

public class AController {
//	static Collection<Form> list = new CopyOnWriteArrayList<Form>();
	protected static Map<String, CopyOnWriteArrayList<Form>> map = new Hashtable<String, CopyOnWriteArrayList<Form>>();
	Executor executor;
	ObjectMapper mapper = new ObjectMapper();
	JsonWriter jw = new JsonWriter();
	
	static {
		if(SoapTemplateManager.isTempatesExsit())
		{
			try {
				map = SoapTemplateManager.readFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.out(e);
			}
		}
	}
//	protected void refreshMap()
//	{
//		if(SoapTemplateManager.isTempatesExsit())
//		{
//			try {
//				map = SoapTemplateManager.readFile();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	
	protected Form fillForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Form form;
		String type = request.getParameter("type").trim();
		
		if("soap".equalsIgnoreCase(type))
		{
			form = new WsForm();
			form.setName(request.getParameter("name"));
			form.setUrl(request.getParameter("url"));
			((WsForm)form).setAction(request.getParameter("action"));
			if(request.getParameter("useSSL").equalsIgnoreCase("true")){
				((WsForm)form).setUseSSL(true);
				((WsForm)form).setProperties(request.getParameter("properties"));
			}else
				((WsForm)form).setUseSSL(false);
			form.setInput(request.getParameter("input"));
			form.setOutput(request.getParameter("output"));
		}else if ("http".equalsIgnoreCase(type))
		{
			form = new HttpForm();
			form.setName(request.getParameter("name"));
			form.setUrl(request.getParameter("url"));
			form.setInput(request.getParameter("input"));
			form.setOutput(request.getParameter("output"));
		}else if ("jms".equalsIgnoreCase(type))
		{
			form = new JmsForm();
			form.setName(request.getParameter("name"));
			form.setInput(request.getParameter("input"));
			form.setOutput(request.getParameter("output"));
			((JmsForm) form).setTrx(request.getParameter("trx"));
			((JmsForm) form).setDestination(request.getParameter("dest"));
			((JmsForm) form).setState(request.getParameter("state"));
		}else
		{
			form = new Form();
			form = new HttpForm();
			form.setName(request.getParameter("name"));
			form.setUrl(request.getParameter("url"));
			form.setInput(request.getParameter("input"));
			form.setOutput(request.getParameter("output"));
		}
		return form;
	}
}
