package com.minxia.execute;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleExtractor {
	
	private String extractFile = "NoIncludedRules.txt" ;
	/** 
	 * @author Min
	 * @date Oct 1, 2014
	 * @return void 
	 * @throws 
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		RuleExtractor re = new RuleExtractor();
//		re.ruleExtract();
//
//	}
	
	public String ruleExtract(InputStream input)
	{
		String ruleStr = null;
		String ruleOut = null;
		List flowLst = new ArrayList();
		
		ruleStr = ruleToString(input);
		if(ruleStr != null)
		{
			flowLst = extractFlowList();
			ruleOut = extractRule(ruleStr, flowLst);
			
		}
		else
		{
			System.out.println("Load agent.clp failed");
		}
		return ruleOut;
//		wirteIvrRule(ruleOut);
//		System.out.println(ruleOut);
		
	}
	
//	private void wirteIvrRule(String ruleOut) {
//		// TODO Auto-generated method stub
//		try {
//			File file = new File(ivrRuleFile);
// 
//			if (!file.exists()) {
//				file.createNewFile();
//			}
// 
//			FileWriter fw = new FileWriter(file.getAbsoluteFile());
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(ruleOut);
//			bw.close();
// 
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}

	private String extractRule(String ruleStr, List flowLst) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer(ruleStr);
		Iterator it = flowLst.iterator();
		
		while(it.hasNext())
		{
			String flow  = it.next().toString()+".vdx";
			int begInx, endInx = -1;
//			String begin = ";BEGIN: File: C:\\Users\\jpyuan\\AppData\\Local\\Temp\\" + flow;
//			String end = ";     File:   C:\\Users\\jpyuan\\AppData\\Local\\Temp\\" + flow;
			String begRegex = "BEGIN: File: .*?"+flow;
			String endRegex = ";     File: .*?"+flow;
			
					
			Matcher begMatcher = Pattern.compile(begRegex, Pattern.CASE_INSENSITIVE).matcher(sb);
			if(begMatcher.find()){
				begInx = begMatcher.start();
				Matcher endMatcher = Pattern.compile(endRegex, Pattern.CASE_INSENSITIVE).matcher(sb);
				if(endMatcher.find()){
					endInx = endMatcher.end();
				}
				if(begInx==-1 || endInx==-1){
					System.out.println("not found "+ flow + "-" + begInx + "-" + endInx);
				}
				try
				{
					sb.replace(begInx, endInx, "");
				}
				catch(Exception e)
				{
					
				}
			}
		}
				
		return sb.toString();
	}

	private List extractFlowList() {
		// TODO Auto-generated method stub
		List flowLst = new ArrayList();
		try
		{
			String sCurrentLine = null;
			BufferedReader br = new BufferedReader(new FileReader(extractFile));
 
			while ((sCurrentLine = br.readLine()) != null) {
				flowLst.add(sCurrentLine.trim());
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return flowLst;
	}

	public String ruleToString(InputStream input)
	{
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try
		{
			br = new BufferedReader(new InputStreamReader(input));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) 
			{
				sb.append(sCurrentLine);
				sb.append("\n");
			}
		}
		catch(IOException e)
		{
		}
		finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return sb.toString();
	}

	public String getExtractFile() {
		return extractFile;
	}

	public void setExtractFile(String extractFile) {
		this.extractFile = extractFile;
	}
	

}
