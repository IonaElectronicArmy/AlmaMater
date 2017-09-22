package com.iona.framework.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

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
