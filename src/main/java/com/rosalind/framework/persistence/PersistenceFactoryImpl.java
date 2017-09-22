package com.rosalind.framework.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.rosalind.framework.persistence.nosql.RosalindNoSqlDao;
import com.rosalind.framework.persistence.nosql.RosalindNoSqlDaoImpl;
import com.rosalind.framework.persistence.rdbms.RosalindRdbmsDao;
import com.rosalind.framework.persistence.rdbms.RosalindRdbmsDaoImpl;

@Component
public class PersistenceFactoryImpl implements PersistenceFactory{
	
	@Autowired
	private RosalindNoSqlDaoImpl rosalindNoSqlDao;
	
	@Autowired
	private RosalindRdbmsDaoImpl rosalindDaoImpl;

	@Override
	public RosalindNoSqlDao getNoSqlDao() {
		return rosalindNoSqlDao;
	}

	@Override
	public RosalindRdbmsDao getRdbmsDao() {
		return rosalindDaoImpl;
	}	
	
}
