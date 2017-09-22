package com.iona.framework.persistence;

import com.iona.framework.persistence.nosql.RosalindNoSqlDao;
import com.iona.framework.persistence.rdbms.RosalindRdbmsDao;

public interface PersistenceFactory {
	
	RosalindNoSqlDao getNoSqlDao();
	RosalindRdbmsDao getRdbmsDao();
}
