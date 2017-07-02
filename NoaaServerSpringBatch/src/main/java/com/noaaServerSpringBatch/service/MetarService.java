package com.noaaServerSpringBatch.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class MetarService {

	private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	
	// testing
	//private int count = 0;
	
	public GZIPInputStream getDownloadFile(Resource inputResource) throws IOException {
		// testing
		// count++;
		// LOGGER.info("count: {}", count);
		//if (count<=2) throw new IOException("getDownloadFile() threw IOException");
		
		GZIPInputStream gzipInputStream = null;
		try {
			gzipInputStream = new GZIPInputStream(new BufferedInputStream(inputResource.getInputStream()));
		} catch (IOException ioException) {
			LOGGER.info("Encountered exception: ", ioException);
			throw ioException;
		}
		return gzipInputStream;
	}

}
