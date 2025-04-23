/**
 * 
 */
package com.pcwk.ehr.cmn;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pcwk.ehr.ed01.AdminDao.Movie;



public interface PLog {
	 Logger LOG = LogManager.getLogger();

	Movie doSelectOne(String title);

	int doDelete(String title);

	List<Movie> doRetrieve();
//	fgdg
	
	
	 
}
