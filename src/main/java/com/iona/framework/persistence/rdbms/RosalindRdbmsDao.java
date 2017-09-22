package com.iona.framework.persistence.rdbms;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public interface RosalindRdbmsDao{
	public void saveOrUpdate(Object contact);

	public void delete(int contactId) ;
	
	public <T> List<T> list(String sql, RowMapper<T> rowMapper);
	
	public Object get(int id);
}
