package com.minxia.jms;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.jms.JmsException;

import com.minxia.log.Log;

/**
 * @author Stony Zhang
 * @date Jul 21, 2011
 */
public class JMSClient {
	public String sendToMQ(final Properties parameters, final String textMessage, final String corID) {
		Log.out("JMSClient.sendToMQ Start, parameters=" + parameters);

		QueueConnection queueConnection = null;
		QueueReceiver queueReceiver = null;
		QueueSender queueSender = null;
		QueueSession queueSession = null;
		String res = null;

		try {
			Properties env = new Properties();
			env.setProperty(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
			env.setProperty(Context.PROVIDER_URL, providerURL);

			InitialContext context = new InitialContext(env);
			Log.out("init context: " + context);
			QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup(connectionJndiName);
			Queue requestQueue = (Queue) context.lookup(reqQueueJndiName);
			Log.out("init request Queue: " + requestQueue.getQueueName());
			Queue responseQueue = (Queue) context.lookup(resQueueJndiName);
			Log.out("init response Queue: " + responseQueue.getQueueName());
			long initconntime = System.currentTimeMillis();
			queueConnection = queueConnectionFactory.createQueueConnection();
			queueConnection.start();
			queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queueSender = queueSession.createSender(requestQueue);
			long endconntime = System.currentTimeMillis();
			Log.out("Init Queue Connection spent: " + (endconntime - initconntime));
			Log.out("init Queue Sender: " + queueSender);
			TextMessage message = queueSession.createTextMessage(textMessage);
			Log.out("Print message header property:");
			for (Iterator iterator = parameters.entrySet().iterator(); iterator.hasNext();) {
				Entry type = (Entry) iterator.next();
				String key = (String) type.getKey();
				String value = (String) type.getValue();
				message.setStringProperty(key, value);
				Log.out("(" + key + " : "+ value + ")\t");
			}
			//String messageSelector = "loggingKey = '" + correlationID + "'";
			message.setStringProperty("loggingKey", corID);
			message.setJMSCorrelationID(corID);
			Log.out("(loggingKey : " + corID + ")\t\n");
			message.setJMSReplyTo(responseQueue);
			long starttime = System.currentTimeMillis();
			queueSender.send(message, javax.jms.DeliveryMode.NON_PERSISTENT,javax.jms.Message.DEFAULT_PRIORITY, 30000);
			Log.out("Send message done");

			String selectorReceive =  "loggingKey = '" + corID + "'";
			queueReceiver = queueSession.createReceiver(responseQueue, selectorReceive);
			Message receiveMsg = queueReceiver.receive(resTimeout);
			Log.out("JMSClient recieve message:" + receiveMsg);
			long endtime = System.currentTimeMillis();
			Properties prop = getMessageHeaders(receiveMsg);
			res = ((TextMessage) receiveMsg).getText();
			Log.out("WFAC transaction time spent: " + (endtime - starttime));
			Log.out("JMSClient recieve message, prop=" + prop + " msgText=" + res);


		} catch (JMSException jmsException) {
			Log.out("JMSClient connection failure", jmsException);
			Log.out("Linked Exception", jmsException.getLinkedException());
		} catch (Exception exception) {
			Log.out("Unexpected exception", exception);
		} finally {
			try {
				if (queueReceiver != null) {
					queueReceiver.close();
					queueReceiver = null;
				}
			} catch (JMSException jmsException) {
				Log.out("Closed JMSClient queueReceiver failure", jmsException);
			}
			try {
				if (queueSender != null) {
					queueSender.close();
					queueSender = null;
				}
			} catch (JMSException jmsException) {
				Log.out("Closed JMSClient queueSender failure", jmsException);
			}
			try {
				if (queueSession != null) {
					queueSession.close();
					queueSession = null;
				}
			} catch (JMSException jmsException) {
				Log.out("Closed JMSClient queueSession failure", jmsException);
			}
			try {
				if (queueConnection != null) {
					queueConnection.stop();
					queueConnection.close();
					queueConnection = null;
				}
			} catch (JMSException jmsException) {
				Log.out("Closed JMSClient queueConnection failure", jmsException);
			}
		}
		Log.out("JMSClient.sendToMQ End");
		return res;
	}
	
	private Properties getMessageHeaders(Message message) {
		Properties prop = new Properties();
		try {
			Enumeration enumer = message.getPropertyNames();
			for (; enumer.hasMoreElements();) {
				String name = (String) enumer.nextElement();
				String value = message.getStringProperty(name);
				prop.setProperty(name, value);
			}
		} catch (JMSException e) {
			Log.out("JMSClient.getMessageHeaders error.",e);
		}
		return prop;
	}
	

	private String initialContextFactory;
	private String providerURL;
	private String connectionJndiName;
	private String reqQueueJndiName;
	private String resQueueJndiName;
	private long resTimeout;
	
	public String getInitialContextFactory() {
		return initialContextFactory;
	}
	public void setInitialContextFactory(String initialContextFactory) {
		this.initialContextFactory = initialContextFactory;
	}
	public String getProviderURL() {
		return providerURL;
	}
	public void setProviderURL(String providerURL) {
		this.providerURL = providerURL;
	}
	public String getConnectionJndiName() {
		return connectionJndiName;
	}
	public void setConnectionJndiName(String connectionJndiName) {
		this.connectionJndiName = connectionJndiName;
	}

	public String getReqQueueJndiName() {
		return reqQueueJndiName;
	}
	public void setReqQueueJndiName(String reqQueueJndiName) {
		this.reqQueueJndiName = reqQueueJndiName;
	}
	public String getResQueueJndiName() {
		return resQueueJndiName;
	}
	public void setResQueueJndiName(String resQueueJndiName) {
		this.resQueueJndiName = resQueueJndiName;
	}
	public long getResTimeout() {
		return resTimeout;
	}
	public void setResTimeout(long resTimeout) {
		this.resTimeout = resTimeout;
	}

}
