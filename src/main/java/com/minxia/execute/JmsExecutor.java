package com.minxia.execute;

import java.text.DecimalFormat;
import java.util.Properties;

import com.minxia.jms.JMSClient;
import com.minxia.model.JmsForm;
import com.minxia.utils.AppContext;
import com.minxia.utils.ConstValues;

public class JmsExecutor implements Executor{
	
	private JmsForm form;
	
	public JmsExecutor(JmsForm form)
	{
		this.form = form;
	}

	@Override
	public void Execute() {
		// TODO Auto-generated method stub
		JMSClient sender = (JMSClient) AppContext.getApplicationContext().getBean("JMSClient");
		Properties constParas = (Properties) AppContext.getApplicationContext().getBean("JMSHeader");
		Properties imsProps = (Properties) AppContext.getApplicationContext().getBean("ImsTrxNameBean");
		
		Properties parameters = new Properties();
		parameters.putAll(constParas);
		parameters.setProperty(ConstValues.DESTINATION, this.form.getDestination());
		String trx = this.form.getTrx();
		String ims_trx = imsProps.getProperty(trx);
		parameters.setProperty(ConstValues.IMSTRANSACTIONNAME, ims_trx);
		String loggingkey = createLoggingKey();
		parameters.setProperty(ConstValues.loggingKey, loggingkey);
		String response = sender.sendToMQ(parameters, form.getInput(), loggingkey);
		this.form.setOutput(response);
	}
	
	private String createLoggingKey() {
		String service = "WFACAccess";
		String flag = "COR";
		String hostname = ConstValues.hostName;
		StringBuffer buffer = new StringBuffer();
		double random = Math.random() * 1000;
		DecimalFormat format = new DecimalFormat("####");
		String randomNumber = format.format(random);
		buffer.append(flag);
		buffer.append(":");
		buffer.append(service);
		buffer.append(hostname);
		buffer.append(System.currentTimeMillis());
		buffer.append(randomNumber);
		buffer.append("|SEQ:0");
		return buffer.toString();
	}


}
