package com.iona.framework.base;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:properties/application.properties")
public class AppConfig {

	@Value("${nosql.username}")
	private String noSqlDBUserName;
	
	@Value("${nosql.password}")
	private String noSqlDBPassword;
	
	@Value("${nosql.database}")
	private String noSqlDatabaseName;
	
	@Value("${rdbms.username}")
	private String rdbmsDBUserName;
	
	@Value("${rdbms.password}")
	private String rdbmsDBPassword;
	
	@Value("${rdbms.database}")
	private String rdbmsDatabaseName;
	
	@Value("${encrypt_token}")
	private boolean encryptToken;
	
	@Value("${page_template}")
	private String pageTemplate;
	
	@Value("${disable_security}")
	private boolean disableSecurity;
	
	@Value("${ldap_enabled}")
	private boolean isLdapEnabled;
	
	@Value("${issuer}")
	private String tokenIssuer;
	
	@Value("${expiryinterval_secs}")
	private String expiryIntervalInSeconds;
	
	@Value("${ws_expiryinterval_secs}")
	private String wsExpiryIntervalInSeconds;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public String getNoSqlDBUserName() {
		return noSqlDBUserName;
	}

	public boolean isEncryptToken() {
		return encryptToken;
	}

	public String getPageTemplate() {
		return pageTemplate;
	}

	public String getNoSqlDBPassword() {
		return noSqlDBPassword;
	}

	public boolean isDisableSecurity() {
		return disableSecurity;
	}

	public String getNoSqlDatabaseName() {
		return noSqlDatabaseName;
	}

	public String getRdbmsDBUserName() {
		return rdbmsDBUserName;
	}

	public String getRdbmsDBPassword() {
		return rdbmsDBPassword;
	}

	public String getRdbmsDatabaseName() {
		return rdbmsDatabaseName;
	}

	public boolean isLdapEnabled() {
		return isLdapEnabled;
	}

	public String getTokenIssuer() {
		return tokenIssuer;
	}

	public String getExpiryIntervalInSeconds() {
		return expiryIntervalInSeconds;
	}

	public String getWsExpiryIntervalInSeconds() {
		return wsExpiryIntervalInSeconds;
	}	

	@PostConstruct
	void print(){
		System.out.println("loaded resource files");
		getTokenIssuer();
	}
	
}