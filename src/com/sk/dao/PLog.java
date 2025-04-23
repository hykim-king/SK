package com.sk.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sk01.AdminDao.Movie;

public interface PLog {
    Logger LOG = LogManager.getLogger();

	int doSave(Movie dto);

	int doUpdate(Movie dto);

	int doDelete(String title);

	Movie doSelectOne(String title);

	List<Movie> doRetrieve();

	Movie doSelectOne(Movie dto);

	List<Movie> doRetrieve(Movie dto);

	int doDelete(Movie dto);
}
