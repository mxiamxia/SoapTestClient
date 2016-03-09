package com.minxia.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.minxia.execute.RuleExtractor;
import com.minxia.log.Log;
import com.minxia.model.Form;
import com.minxia.utils.SoapTemplateManager;

@Controller
public class IvrController {

	/** 
	 * @author Min
	 * @date Aug 19, 2015
	 * @return void 
	 * @throws 
	 */
	@Autowired
	private RuleExtractor ruleExtractor;
	
	@RequestMapping(value = "ivr.mx", method = RequestMethod.GET)
	public String loadIvrPage() 
	{
		return "ivr";
	}
	
	@RequestMapping(value = "/uploadFile.mx", method = RequestMethod.POST)
    public void uploadFileHandler(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
		
        if (!file.isEmpty()) {
        	try {
        		InputStream inputStream = file.getInputStream();
        		String ivr_rule = ruleExtractor.ruleExtract(inputStream);
        		if(ivr_rule != null) {
        			InputStream is = new ByteArrayInputStream(ivr_rule.getBytes(StandardCharsets.UTF_8));
        			// MIME type of the file
        			response.setContentType("application/octet-stream");
        			// Response header
        			response.setHeader("Content-Disposition", "attachment; filename=\""
        					+ "ivr.clp" + "\"");
        			// Read from the file and write into the response
        			OutputStream os = response.getOutputStream();
        			byte[] buffer = new byte[1024];
        			int len;
        			while ((len = is.read(buffer)) != -1) {
        				os.write(buffer, 0, len);
        			}
        			os.flush();
        			os.close();
        			is.close();
        		}
        		
        	} catch (Exception e) {
        		e.printStackTrace();
            }
        } else {
        	try {
				response.getWriter().write("The File is empty");
				response.getWriter().flush();
	    		response.getWriter().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

	public void setRuleExtractor(RuleExtractor ruleExtractor) {
		this.ruleExtractor = ruleExtractor;
	}

}
