package com.iona.framework.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
setterVisibility = JsonAutoDetect.Visibility.NONE)
public class Test {
	
	@JsonProperty("Webservice_Status")
	private String webserviceStatus;
	
	@JsonProperty("NOSQL_Status")
	private String noSqlStatus;
	
	@JsonProperty("RDBMS_Status")
	private String rdbmsStatus;

	public String getStatus() {
		return webserviceStatus;
	}

	public void setStatus(String status) {
		this.webserviceStatus = status;
	}

	public String getWebserviceStatus() {
		return webserviceStatus;
	}

	public void setWebserviceStatus(String webserviceStatus) {
		this.webserviceStatus = webserviceStatus;
	}

	public String getNoSqlStatus() {
		return noSqlStatus;
	}

	public void setNoSqlStatus(String noSqlStatus) {
		this.noSqlStatus = noSqlStatus;
	}

	public String getRdbmsStatus() {
		return rdbmsStatus;
	}

	public void setRdbmsStatus(String rdbmsStatus) {
		this.rdbmsStatus = rdbmsStatus;
	}
	

}
