package com.minxia.utils;

import org.springframework.context.ApplicationContext;

public class AppContext {
	private static ApplicationContext ctx;
	private static Object lock = new Object();

    /**
     * Injected from the class "ApplicationContextProvider" which is automatically
     * loaded during Spring-Initialization.
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
        synchronized (lock) {
			lock.notifyAll();
		}
    }

    /**
     * Get access to the Spring ApplicationContext from everywhere in your Application.
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
    
    public static Object getBean(String name)
    {

		if (ctx == null) {
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return ctx.getBean(name);
	
    }
}
