package com.iona.framework.business;

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

import com.iona.framework.Constants;
import com.iona.framework.dto.Person;
import com.iona.framework.persistence.PersistenceFactory;

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
		
		RowMapper<com.iona.framework.Person> rowMapper = new RowMapper<com.iona.framework.Person>() {
			@Override
			public com.iona.framework.Person mapRow(ResultSet rs, int rowNum) throws SQLException {
				com.iona.framework.Person person = new com.iona.framework.Person();

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
