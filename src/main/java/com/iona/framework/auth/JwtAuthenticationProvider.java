package com.iona.framework.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.iona.framework.util.RosalindException;

@Component(value = "jwtAuthenticationProvider")
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	
	private static final String JWT_INVALID_ERROR_MSG = "JWT token is not valid";
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
		String token = jwtAuthenticationToken.getToken();

		User parsedUser;
		try {
			parsedUser = jwtUtil.parseToken(token);
		} catch (RosalindException e) {
			throw new RosalindAuthenticationException(e.getMessage());
		}
		if (parsedUser == null) {
			throw new JwtTokenMalformedException(JWT_INVALID_ERROR_MSG);
		}
		List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());
		return new AuthenticatedUser(parsedUser.getId(), parsedUser.getUserName(), token, authorityList);
	}

	private class RosalindAuthenticationException extends AuthenticationException {

		private static final long serialVersionUID = -4151977989836823366L;

		public RosalindAuthenticationException(String msg) {
			super(msg);
		}

	}
}
