package com.minxia.jms;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.minxia.log.Log;

public class MQJMSClient {

	/** 
	 * @author Min
	 * @date Feb 22, 2016
	 * @return void 
	 * @throws 
	 */

	private String QCF_URL;
	private InitialContext ictx = null;
	private QueueConnectionFactory qcf = null;
	private QueueConnection connection = null;
	private QueueSession session = null;
	private boolean transacted = false;
	
	private InitialContext getInitialContext() {
		if (ictx == null)
			try {
				ictx = new InitialContext();
			} catch (NamingException nex) {
				Log.out(nex);
			}
		return ictx;
	}
	
	private QueueConnectionFactory getQCF() {
		if(qcf == null) {
			try {
				qcf = (QueueConnectionFactory) this.getInitialContext().lookup(getQCF_URL());
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return qcf;
	}
	
	private Queue getQ(String QJNDI) {
		Queue queue = null;
		try {
			queue = (Queue) getInitialContext().lookup(QJNDI);
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return queue;
	}
	
	private void startQSession(QueueConnectionFactory factory) {
		try {
			connection = null;
			connection = factory.createQueueConnection();
			session = null;
			session = connection.createQueueSession(transacted, QueueSession.AUTO_ACKNOWLEDGE);
			connection.start();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List browserQMessages(String JNDI) throws JMSException {
		List messages = new ArrayList();
		Queue queue = null;
		this.startQSession(this.getQCF());
		queue = this.getQ(JNDI);
		if(queue == null) {
			throw new JMSException("queue is not exist");
		}
		QueueBrowser browser = session.createBrowser(queue);
		Enumeration msgs = browser.getEnumeration();
		if ( !msgs.hasMoreElements() ) { 
		    Log.out("No messages in queue");
		} else { 
		    while (msgs.hasMoreElements()) { 
		        Message tempMsg = (Message)msgs.nextElement(); 
		        messages.add(tempMsg);
		        System.out.println(tempMsg.toString());
		        Log.out("Message: " + tempMsg); 
		    }
		}
		connection.stop();
		return messages;
	}

	public String getQCF_URL() {
		return QCF_URL;
	}

	public void setQCF_URL(String qCF_URL) {
		QCF_URL = qCF_URL;
	}

}
