package com.noaaServerSpringBatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

public class RetryLogger extends RetryListenerSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	public RetryLogger() {
		super();
		LOGGER.info("Initialized ...");
	}
	
	@Override
	public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
		LOGGER.warn("Encounterd exception: ", throwable);
		LOGGER.warn("Retrying ...");
		super.onError(context, callback, throwable);
	}

}
