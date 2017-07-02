package com.noaaServerSpringBatch.main;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MetarDoanloadBatch {

	private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	public static void main(String[] args) {

		String[] springConfig = { "metarDoanloadJob.xml" };

		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(springConfig)) {

			JobLauncher metarDoanloadJob = (JobLauncher) context.getBean("syncJobLauncher");
			Job job = (Job) context.getBean("metarDoanloadJob");

			try {
				JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
				jobParametersBuilder.addLong("sysTime", new Date().getTime())
						// .addString("inputResource",
						// "file:/Downloads/metars.cache.xml.gz")
						.addString("inputResource",
								"url:https://aviationweather.gov/adds/dataserver_current/current/metars.cache.xml.gz")
						// .addString("targetDirectory", "./work")
						.addString("targetFile", "metars.xml");
				JobExecution execution = metarDoanloadJob.run(job, jobParametersBuilder.toJobParameters());
				LOGGER.info("Exit Status: {}", execution.getStatus());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
