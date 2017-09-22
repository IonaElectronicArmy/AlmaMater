package com.rosalind.framework.persistence;

import com.rosalind.framework.persistence.nosql.RosalindNoSqlDao;
import com.rosalind.framework.persistence.rdbms.RosalindRdbmsDao;

public interface PersistenceFactory {
	
	RosalindNoSqlDao getNoSqlDao();
	RosalindRdbmsDao getRdbmsDao();
}
