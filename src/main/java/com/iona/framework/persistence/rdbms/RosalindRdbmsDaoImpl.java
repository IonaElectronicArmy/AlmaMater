package com.iona.framework.persistence.rdbms;

import java.util.List;

import javax.annotation.PostConstruct;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.iona.framework.base.AppConfig;

@Component
public class RosalindRdbmsDaoImpl implements RosalindRdbmsDao {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AppConfig appConfig;
	
	@PostConstruct
	void init(){
		System.out.println("init of RosalindRdbmsDaoImpl");
		PGPoolingDataSource dataSource = new PGPoolingDataSource();
		//dataSource.setDriverClassName("org.postgresql.Driver");
		
		//dataSource.setUrl("jdbc:postgresql://localhost:5432/Test");
		dataSource.setServerName("localhost");
		dataSource.setDatabaseName(appConfig.getRdbmsDatabaseName());
		dataSource.setUser(appConfig.getRdbmsDBUserName());
		dataSource.setPassword(appConfig.getRdbmsDBPassword());
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void saveOrUpdate(Object contact) {
	}

	@Override
	public void delete(int contactId) {
	}

	@Override
	public <T> List<T> list(String sql, RowMapper<T> rowMapper) {		
		List<T> listContact = jdbcTemplate.query(sql, rowMapper);
		return listContact;
	}

	@Override
	public Object get(int id) {
		return null;
	}

}
