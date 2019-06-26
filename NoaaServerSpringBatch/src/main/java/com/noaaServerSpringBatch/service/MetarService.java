package com.noaaServerSpringBatch.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.noaaServerSpringBatch.exception.ApplicationException;

/**
 * Methods in this class are retried using aop and com.noaaServerSpringBatch.listener.RetryLogger
 *
 */
@Service
public class MetarService {

	public GZIPInputStream getDownloadFile(Resource inputResource) throws ApplicationException {
		
		GZIPInputStream gzipInputStream = null;
		try {
			gzipInputStream = new GZIPInputStream(new BufferedInputStream(inputResource.getInputStream()));
		} catch (IOException ioException) {
			throw new ApplicationException(String.format("Failed to download file %s", inputResource.toString()), ioException);
		}
		return gzipInputStream;
	}

}
