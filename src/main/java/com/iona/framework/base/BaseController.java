package com.iona.framework.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iona.framework.Constants;
import com.iona.framework.auth.JwtUtil;
import com.iona.framework.business.TestUrlManager;
import com.iona.framework.dto.Test;
import com.iona.framework.util.RosalindException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
public class BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
	private static final String USER_NAME_CONSTANT = "userName";
	private static final String PASSWORD_CONSTANT = "password";
	private static final String FORWARDED_FOR_CONSTANT = "X-FORWARDED-FOR";
	private static final String USER_AGENT_CONSTANT = "User-Agent";
	private static final String APP_TEXT = "app";
	private static final String JSON_MIME_TYPE = "application/json";
	private static final String USER_NAME_MANDATORY_MSG = "User Name is mandatory";
	private static final String PASSWORD_NAME_MANDATORY_MSG = "Password is mandatory";
	
	@Autowired
	TestUrlManager testUrlManager;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@Autowired
    private AppConfig appConfig;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ApiOperation(value = "Returns test result", notes = "Returns test result", response = String.class)
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Test success", response = String.class),
	    @ApiResponse(code = 404, message = "Failure"),
	    @ApiResponse(code = 500, message = "Internal server error")}
	)
	public ResponseEntity<Test> testUrl() {
		LOG.debug("test {}", "executed");
		Test testObject = new Test();
		testObject.setStatus("Test Success");		
		Map<Constants, Boolean> testResultMap = testUrlManager.testUrls();		
		testObject.setNoSqlStatus(testResultMap.get(Constants.NOSQL_DB_TEST_STATUS) ? "Test Sucess" : "Test Failed");
		testObject.setRdbmsStatus(testResultMap.get(Constants.RDBMS_TEST_STATUS) ? "Test Sucess" : "Test Failed");
		return new ResponseEntity<>(testObject, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = JSON_MIME_TYPE)
	public ResponseEntity<Map<String,Object>> authenticateUser(
			@RequestBody String userLoginDetails,
			@RequestHeader(required = false, value = FORWARDED_FOR_CONSTANT) String ipAddress, 
			@RequestHeader(USER_AGENT_CONSTANT) String userAgent,
			@RequestHeader(required = false, value = APP_TEXT)String appHeader,
			HttpServletRequest request) {//@QueryParam("user") 
		boolean isSuccess = true;
		String msg = "";
		Map<String,Object> resultJsonObject = new HashMap<>();
		try {
			JSONObject jsonObject = new JSONObject(userLoginDetails);
			String userId = jsonObject.get(USER_NAME_CONSTANT).toString();
			String password = jsonObject.get(PASSWORD_CONSTANT).toString();
			
			if(userId == null || userId.isEmpty()){
				throw new RosalindException(USER_NAME_MANDATORY_MSG);
			}
			if(password == null || password.isEmpty()){
				throw new RosalindException(PASSWORD_NAME_MANDATORY_MSG);
			}
						
			if(!appConfig.isDisableSecurity()) {				
				if (ipAddress == null) {
					ipAddress = request.getRemoteAddr();
				}
				String jwt = jwtUtil.generateToken(userId, "admin", ipAddress, userAgent, appHeader);
				resultJsonObject.put("expires", appConfig.getExpiryIntervalInSeconds());								
			//	resultJsonObject.put(ROLE, "admin");
			//	resultJsonObject.put(USER_NAME_CONSTANT, userId);
			//	resultJsonObject.put(USER_ID, userId);
				resultJsonObject.put("permissions", null);		
				resultJsonObject.put("image", null);				
				resultJsonObject.put("token", jwt);				
			} else{
				String jwt = jwtUtil.generateDummyToken(ipAddress, userAgent, appHeader);
				resultJsonObject.put("expires", appConfig.getExpiryIntervalInSeconds());								
				resultJsonObject.put("permissions", null);		
				resultJsonObject.put("image", null);				
				resultJsonObject.put("token", jwt);
			}			
		} catch (RosalindException e) {
			isSuccess = false;
			msg = e.getMessage();
			LOG.error(e.getMessage(), e);
		} catch (JSONException e) {
			isSuccess = false;
			msg = e.getMessage();
			LOG.error(e.getMessage(), e);
		} 

		if (!isSuccess) {
			resultJsonObject.put("error", msg);
			return new ResponseEntity<>(resultJsonObject, HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>(resultJsonObject, HttpStatus.OK);
		}
		
	
	}
	
	@RequestMapping(value = "/loginFailed", method = RequestMethod.POST, produces = JSON_MIME_TYPE)
	public String page()
	{
		return "sdsdfsfdssfsfds";
	}
}
