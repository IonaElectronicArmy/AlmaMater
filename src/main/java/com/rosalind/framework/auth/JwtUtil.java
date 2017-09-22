package com.rosalind.framework.auth;

import java.security.Key;
import java.util.Calendar;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rosalind.framework.Constants;
import com.rosalind.framework.base.AppConfig;
import com.rosalind.framework.util.RosalindException;
import com.rosalind.framework.util.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    
	private static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);
	private static final String USER_ID = "userId";
	private static final String ROLE = "role";
	private static final String SLASH = "\\";
	private static final String ISSUER = "ROSALIND.COM";
	
    @Autowired
    AppConfig appConfig;

    
    public User parseToken(String token) throws RosalindException {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(Security.getSecurity().getKey())
                    .parseClaimsJws(token)
                    .getBody();

            User user = new User();
            user.setUserName(body.getSubject());
            user.setId((String) body.get(USER_ID));
            user.setRole((String) body.get(ROLE));

            return user;

        } catch (JwtException | ClassCastException e) {
            LOG.error(e.getMessage(), e);
            throw new RosalindException(e);
        } catch (IllegalArgumentException e) {
        	LOG.error(e.getMessage(), e);
            throw new RosalindException(e);
		}
    }

   
	public String generateToken(String userId, String role, String ipAddress, String userAgent, String appHeader)
			throws RosalindException {
		String source = "";
		int expiryIntervalInSecs = 0;
		Key key = Security.getSecurity().getKey();
		
		if (userAgent != null) {
			source = ipAddress + SLASH + userAgent;
		} else {
			source = ipAddress;
		}
		if (appConfig.isEncryptToken()) {
			source = Security.getSecurity().encrypt(source);
		}		
		if (appHeader != null && Constants.app.name().equals(appHeader)) {
			expiryIntervalInSecs = Integer.parseInt(appConfig.getExpiryIntervalInSeconds());
		} else {
			expiryIntervalInSecs = Integer.parseInt(appConfig.getWsExpiryIntervalInSeconds());
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, expiryIntervalInSecs);
		

		Claims claims = Jwts.claims().setSubject(userId);
		claims.put(USER_ID, userId);
		claims.put(ROLE, role);
		return Jwts.builder()
				.setIssuer(ISSUER)
				.setClaims(claims)
				.setIssuedAt(Calendar.getInstance().getTime())
				.setAudience(source)
				.setId(UUID.randomUUID().toString() + UUID.randomUUID().toString())
				.setExpiration(calendar.getTime())
				.signWith(SignatureAlgorithm.HS256, key)
				.compact();
	}
	
	
	public String generateDummyToken(String ipAddress, String userAgent, String appHeader)
			throws RosalindException {
		String source = "";
		int expiryIntervalInSecs = 0;
		Key key = Security.getSecurity().getKey();
		
		if (userAgent != null) {
			source = ipAddress + SLASH + userAgent;
		} else {
			source = ipAddress;
		}
		if (appConfig.isEncryptToken()) {
			source = Security.getSecurity().encrypt(source);
		}		
		if (appHeader != null && Constants.app.name().equals(appHeader)) {
			expiryIntervalInSecs = Integer.parseInt(appConfig.getExpiryIntervalInSeconds());
		} else {
			expiryIntervalInSecs = Integer.parseInt(appConfig.getWsExpiryIntervalInSeconds());
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, expiryIntervalInSecs);
		

		Claims claims = Jwts.claims().setSubject("dummy");
		claims.put(USER_ID, "dummy");
		claims.put(ROLE, "dummyrole");
		return Jwts.builder()
				.setIssuer(ISSUER)
				.setClaims(claims)
				.setIssuedAt(Calendar.getInstance().getTime())
				.setAudience(source)
				.setId(UUID.randomUUID().toString() + UUID.randomUUID().toString())
				.setExpiration(calendar.getTime())
				.signWith(SignatureAlgorithm.HS256, key)
				.compact();
	}
}
