package com.iona.framework.auth;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@ImportResource("classpath:securityContext.xml")
@Configuration
public class RootConfig {

   /* @Bean
    public DatabaseService databaseService() {
        return new DefaultDatabaseService();
    }*/

    @Bean
    public ExceptionMappingAuthenticationFailureHandler authExceptionMapping() {
        final ExceptionMappingAuthenticationFailureHandler emafh = new ExceptionMappingAuthenticationFailureHandler();
        emafh.setDefaultFailureUrl("/api/loginFailed");
        final Map<String, String> mappings = new HashMap<>();
        mappings.put(CredentialsExpiredException.class.getCanonicalName(), "/change_password");
        emafh.setExceptionMappings(mappings);
        return emafh;
    }
    
    
}
