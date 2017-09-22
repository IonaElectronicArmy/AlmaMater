package com.rosalind.framework.business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.rosalind.framework.Constants;
import com.rosalind.framework.dto.Person;
import com.rosalind.framework.persistence.PersistenceFactory;

@Component
public class TestUrlManager {
	@Autowired
	PersistenceFactory persistenceFactory;
	
	public Map<Constants, Boolean> testUrls(){
		Map<Constants, Boolean> testResultMap = new EnumMap<>(Constants.class);
		boolean noSqlStatus = false;
		boolean rdbmsStatus = false;
		Query searchUserQuery = new Query(Criteria.where("age").is(10));
		List<?> personList;
		try {
			personList = persistenceFactory.getNoSqlDao().get(searchUserQuery, Person.class);
			System.out.println(personList);
			noSqlStatus = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RowMapper<com.rosalind.framework.Person> rowMapper = new RowMapper<com.rosalind.framework.Person>() {
			@Override
			public com.rosalind.framework.Person mapRow(ResultSet rs, int rowNum) throws SQLException {
				com.rosalind.framework.Person person = new com.rosalind.framework.Person();

				person.setActive(rs.getBoolean("Active"));
				person.setAge(rs.getInt("Age"));
				person.setCreatedDate(rs.getDate("CreatedDate"));
				person.setName(rs.getString("Name"));
				return person;
			}

		};
		try {
			String query = "select * from testschema.\"MyTable\"";
			persistenceFactory.getRdbmsDao().list(query, rowMapper);
			rdbmsStatus = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		testResultMap.put(Constants.NOSQL_DB_TEST_STATUS, noSqlStatus);
		testResultMap.put(Constants.RDBMS_TEST_STATUS, rdbmsStatus);
		return testResultMap;
	}
}
