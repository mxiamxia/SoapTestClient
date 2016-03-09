package com.minxia.log;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {

	/** 
	 * @author Min
	 * @date Dec 18, 2014
	 * @return void 
	 * @throws 
	 */
		private static Logger logger = Logger.getLogger("NtelTools");
		
		static{
			
			System.out.println(Log.class.getResource("/"));
//			String config = System.getProperty("user.dir") + File.separator + "src/main/webapp/WEB-INF/config/ntelTest.properties";
			
//			String ntel_dir = System.getProperty("NTEL.DIR");
//			String config = ntel_dir + File.separator + "bis/ntelTest.properties";
//			System.out.println(config);
			PropertyConfigurator.configure("ntelTest.properties");
//			PropertyConfigurator.configure(Constants.testDir+"/bis/sharedQueueLog.properties");
		}
		public static String timeFormat = "yyyy-MM-dd HH:mm:ss";

		public static void out(String mes) {
			logger.info(mes);
		}
		
		public static void out(String mes, Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		public static void out(Exception e){
			logger.error(e.getMessage(), e);
		}

		public static void main(String[] args) throws Exception {
	        try{
	        	String p = null;
	        	p.equals("ddd");
	        }catch(Exception e){
	        	Log.out(e);
	        }
			out("This is a test");
			Thread.sleep(1000);
			out("This is a test");
		}


}
