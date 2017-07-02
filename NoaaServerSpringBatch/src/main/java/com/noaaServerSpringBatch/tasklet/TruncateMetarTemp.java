package com.noaaServerSpringBatch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.noaaServerSpringBatch.dao.MetarDao;

public class TruncateMetarTemp implements Tasklet {

	private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	@Autowired
	private MetarDao metarDao;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		LOGGER.info("Truncating table metar_temp");
		metarDao.truncateMetarTemp();
		LOGGER.info("Table truncated");
		return RepeatStatus.FINISHED;
	}

}
