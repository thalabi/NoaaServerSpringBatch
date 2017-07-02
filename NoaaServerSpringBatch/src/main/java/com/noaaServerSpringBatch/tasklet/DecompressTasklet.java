package com.noaaServerSpringBatch.tasklet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.noaaServerSpringBatch.service.MetarService;

@Component
public class DecompressTasklet implements Tasklet {

	private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private static final String WORK_DIR = "NoaDataserverWork";

	@Autowired
	private MetarService metarService;
	@Value("#{jobParameters['inputResource']}")
	private Resource inputResource;
	@Value("#{jobParameters['targetDirectory']}") // default is java.io.tmpdir/WORK_DIR
	private String targetDirectory;
	@Value("#{jobParameters['targetFile']}")
	private String targetFile;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		LOGGER.debug("inputResource: {}", inputResource.toString());

		GZIPInputStream zis = metarService.getDownloadFile(inputResource);
		
		String effectiveTargetDirectory = StringUtils.isEmpty(targetDirectory) ? System.getProperty("java.io.tmpdir")+File.separator+WORK_DIR : targetDirectory;
		LOGGER.debug("effectiveTargetDirectory: {}", effectiveTargetDirectory);
		File targetDirectoryAsFile = new File(effectiveTargetDirectory);
		if (!targetDirectoryAsFile.exists()) {
			FileUtils.forceMkdir(targetDirectoryAsFile);
		}
		File target = new File(effectiveTargetDirectory, targetFile);
		BufferedOutputStream dest = null;
		if (!target.exists()) {
			target.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(target);
		dest = new BufferedOutputStream(fos);
		IOUtils.copy(zis, dest);
		dest.flush();
		dest.close();
		zis.close();
		if (!target.exists()) {
			throw new IllegalStateException("Could not decompress anything from the archive!");
		}
		
		ExecutionContext jobExecutionContext = chunkContext.getStepContext()
				.getStepExecution().getJobExecution()
				.getExecutionContext();

		jobExecutionContext.putString("effectiveTargetDirectory", effectiveTargetDirectory);
		
		return RepeatStatus.FINISHED;
	}

}
