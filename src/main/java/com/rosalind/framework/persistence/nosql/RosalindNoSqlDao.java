package com.rosalind.framework.persistence.nosql;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

public interface RosalindNoSqlDao{
	public <T> List<T> get(Query query, Class<T> claaz);
}
