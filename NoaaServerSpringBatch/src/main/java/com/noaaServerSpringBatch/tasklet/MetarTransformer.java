package com.noaaServerSpringBatch.tasklet;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import com.noaaServerSpringBatch.schema.metar.METAR;
import com.noaaServerSpringBatch.schema.metar.Response;

public class MetarTransformer implements Tasklet {

	private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	
	@Value("#{jobParameters['targetFile']}")
	private String targetFile;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		ExecutionContext jobExecutionContext = chunkContext.getStepContext()
				.getStepExecution().getJobExecution()
				.getExecutionContext();

		String effectiveTargetDirectory = jobExecutionContext.getString("effectiveTargetDirectory");
		File target = new File(effectiveTargetDirectory, targetFile);

		
		JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		Response response = (Response) jaxbUnmarshaller.unmarshal(target);
		List<METAR> metarList = response.getData().getMETAR();
		LOGGER.info("{} metars received", metarList.size());

		jobExecutionContext.put("metarList", metarList);
		
		return RepeatStatus.FINISHED;
	}
}
