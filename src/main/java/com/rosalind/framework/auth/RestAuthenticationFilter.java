package com.rosalind.framework.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosalind.framework.Constants;
import com.rosalind.framework.base.AppConfig;
import com.rosalind.framework.util.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component(value="fChain")
public class RestAuthenticationFilter implements Filter {
	public static final String AUTHENTICATION_HEADER = "Authorization";
	// use http://www.freeformatter.com/java-dotnet-escape.html#ad-output to
	// un-escape the string in page_template in config.properties
	Logger LOG = LoggerFactory.getLogger(RestAuthenticationFilter.class);
	private static final String CONSTANT_PART = "/index.html#/invokeService";
	private static final String X_FORWARDED_FOR = "X-FORWARDED-FOR";
	private static final String URL_AUTHENTICATE = "/webservice/authenticate";
	private static final String TOKEN_STRING = "token";
	private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
	private static final String LOCALHOST_STRING = "localhost";
	private static final String USER_AGENT_STRING = "User-Agent";
	private static final String EMPTY_STRING = "";
	private static final String DOT_STRING = ".";
	private static final String REVERSE_SLASH = "\\";
	private static final String DOLLAR = "$";
	private static final String COLON = ":";
	private static final String TILDE = "~";
	private FilterConfig filterConfig;

	@Autowired
	private AppConfig appConfig;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			String reason = EMPTY_STRING;
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String url = httpServletRequest.getPathInfo();
			String ipAddress = httpServletRequest.getHeader(X_FORWARDED_FOR);
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			boolean authenticationStatus = false;
			if (URL_AUTHENTICATE.equals(url) || appConfig.isDisableSecurity()) {
				authenticationStatus = true;
			} else {
				String jwt = httpServletRequest.getHeader(TOKEN_STRING);
				if (jwt != null) {
					try {
						jwt = jwt.substring(7);
						if ("null".equals(jwt)) {
							jwt = null;
							throw new Exception("Token missing");
						}
						Key key = Security.getSecurity().getKey();
						Jws<Claims> jwtClaims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
						String userAgent = httpServletRequest.getHeader(USER_AGENT_STRING);
						String source = EMPTY_STRING;
						if (userAgent != null) {
							source = ipAddress + REVERSE_SLASH + userAgent;
						} else {
							source = ipAddress;
						}
						//boolean encryptToken = Boolean.parseBoolean();
						String sourceToCompare = null;
						if (appConfig.isEncryptToken()) {
							sourceToCompare = Security.getSecurity().decrypt(jwtClaims.getBody().getAudience());
						} else {
							sourceToCompare = jwtClaims.getBody().getAudience();
						}
						if (!sourceToCompare.equals(source)) {
							throw new Exception("Invalid token");
						}
						authenticationStatus = true;
					} catch (io.jsonwebtoken.ExpiredJwtException je) {
						je.printStackTrace();
						if (jwt != null) {
							int startIndex = jwt.indexOf(DOT_STRING);
							byte[] decodedBytes = Base64.getDecoder()
									.decode(jwt.substring(startIndex + 1, jwt.indexOf(DOT_STRING, startIndex + 1)));
							LOG.info("JWT Claim " + new String(decodedBytes));
						}
						LOG.info("url " + url);
						authenticationStatus = false;
						reason = "Token exipred";
					} catch (Exception e) {
						e.printStackTrace();
						if (jwt != null) {
							int startIndex = jwt.indexOf(DOT_STRING);
							byte[] decodedBytes = Base64.getDecoder()
									.decode(jwt.substring(startIndex + 1, jwt.indexOf(DOT_STRING, startIndex + 1)));
							LOG.info("JWT Claim " + new String(decodedBytes));
						}
						LOG.info("url " + url);
						authenticationStatus = false;
						reason = e.getMessage();
					}

				} else {
					authenticationStatus = false;
					LOG.info("url " + url);
					reason = "Login information missing";
				}

			}

			if (authenticationStatus) {
				filter.doFilter(request, response);
			} else {
				if (response instanceof HttpServletResponse) {
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					PrintWriter writer = httpServletResponse.getWriter();
					String appHeader = httpServletRequest.getHeader(Constants.app.name());
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					if(Constants.app.name().equals(appHeader)){
						ObjectMapper mapper = new ObjectMapper();
						Map<String,String> payload = new HashMap<>(1);
						payload.put("reason", reason);					
						writer.write(mapper.writeValueAsString(payload));
					} else {
						if (LOCALHOST_IPV6.equals(ipAddress)) {
							ipAddress = LOCALHOST_STRING;
						}
						String path = httpServletRequest.getScheme() + "://" + ipAddress + COLON
								+ httpServletRequest.getLocalPort() + httpServletRequest.getContextPath() + CONSTANT_PART;
						String pageTemplate = appConfig.getPageTemplate().replace(DOLLAR, path)
								.replace(TILDE, reason);						
						writer.write(pageTemplate);
					}
					
					writer.flush();
					writer.close();

				}
			}
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
	}

}
