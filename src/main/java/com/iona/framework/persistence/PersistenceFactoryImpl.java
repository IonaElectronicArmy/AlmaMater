package com.iona.framework.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iona.framework.persistence.nosql.IonaNoSqlDao;
import com.iona.framework.persistence.nosql.IonaNoSqlDaoImpl;
import com.iona.framework.persistence.rdbms.IonaRdbmsDao;
import com.iona.framework.persistence.rdbms.IonaRdbmsDaoImpl;

@Component
public class PersistenceFactoryImpl implements PersistenceFactory{
	
	@Autowired
	private IonaNoSqlDaoImpl rosalindNoSqlDao;
	
	@Autowired
	private IonaRdbmsDaoImpl rosalindDaoImpl;

	@Override
	public IonaNoSqlDao getNoSqlDao() {
		return rosalindNoSqlDao;
	}

	@Override
	public IonaRdbmsDao getRdbmsDao() {
		return rosalindDaoImpl;
	}	
	
}
