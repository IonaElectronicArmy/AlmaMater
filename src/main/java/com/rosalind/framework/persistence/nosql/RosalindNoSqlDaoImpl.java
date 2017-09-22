package com.rosalind.framework.persistence.nosql;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.rosalind.framework.base.AppConfig;

@Component
public class RosalindNoSqlDaoImpl implements RosalindNoSqlDao {

	@Autowired
	private AppConfig appConfig;

	private MongoTemplate mongoTemplate;

	@PostConstruct
	public void init() {
		ServerAddress serverAddress = new ServerAddress("localhost", 27017);
		ArrayList<ServerAddress> mongoSeedListID = new ArrayList<>();
		mongoSeedListID.add(serverAddress);

		MongoCredential mongoCredential = MongoCredential.createCredential(appConfig.getNoSqlDBUserName(),
				appConfig.getNoSqlDatabaseName(), appConfig.getNoSqlDBPassword().toCharArray());
		ArrayList<MongoCredential> mongoCredentialListID = new ArrayList<>();
		mongoCredentialListID.add(mongoCredential);

		MongoClient mongoClient = new MongoClient(mongoSeedListID, mongoCredentialListID);
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, appConfig.getNoSqlDatabaseName());
		mongoTemplate = new MongoTemplate(mongoDbFactory);

		mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
		mongoTemplate.setWriteConcern(WriteConcern.ACKNOWLEDGED);
	}

	@Override
	public <T> List<T> get(Query searchUserQuery, Class<T> claaz) {
		return mongoTemplate.find(searchUserQuery, claaz);
	}

}
