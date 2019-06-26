package com.noaaServerSpringBatch.dao;

import org.apache.ibatis.annotations.Param;

import com.noaaServerSpringBatch.schema.metar.METAR;

public interface MetarDao {

	int truncateMetarTemp();
	
	int insertMetarTemp (
	   	@Param("metar") METAR metar);

	int mergeMetar();
	
	int createBackupTempTable(@Param("timestamp") String timestamp);
	int copyMetarTempTable(@Param("timestamp") String timestamp);
}


