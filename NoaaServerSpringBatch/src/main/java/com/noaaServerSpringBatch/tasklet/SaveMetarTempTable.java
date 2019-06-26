package com.noaaServerSpringBatch.tasklet;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.noaaServerSpringBatch.dao.MetarDao;

public class SaveMetarTempTable implements Tasklet {

	private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	@Autowired
	private MetarDao metarDao;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		LOGGER.info("Creating backup table metar_temp_{}", timestamp);
		int copyCount = metarDao.copyMetarTempTable(timestamp);
		LOGGER.info("{} rows copied", copyCount);
		if (copyCount == 0) {
			throw new IllegalStateException("Failed to make a backup of metar_temp table");
		}
		return RepeatStatus.FINISHED;
	}

}
