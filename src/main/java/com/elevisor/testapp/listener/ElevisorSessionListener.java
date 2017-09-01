package com.elevisor.testapp.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by kranian on 17. 9. 1.
 */
public class ElevisorSessionListener implements HttpSessionListener {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    final public static AtomicLong sessionCount = new AtomicLong(0);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.info("sessionCreated >" , sessionCount.incrementAndGet());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
//        logger.info("sessionDestroyed > " , sessionCount.decrementAndGet());

    }
    public static long getTotalSessionCount() {
        return sessionCount.get();
    }
}
