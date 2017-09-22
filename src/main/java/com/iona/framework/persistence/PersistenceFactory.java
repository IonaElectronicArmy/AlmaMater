package com.iona.framework.persistence;

import com.iona.framework.persistence.nosql.IonaNoSqlDao;
import com.iona.framework.persistence.rdbms.IonaRdbmsDao;

public interface PersistenceFactory {
	
	IonaNoSqlDao getNoSqlDao();
	IonaRdbmsDao getRdbmsDao();
}
