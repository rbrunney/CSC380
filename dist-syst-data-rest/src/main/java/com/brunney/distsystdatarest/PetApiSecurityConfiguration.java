package com.brunney.distsystdatarest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class PetApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PetUserDetailsService petUserDetailsService() {
        return new PetUserDetailsService();
    }

    public void configure(HttpSecurity http) throws Exception {
        // Here is where we set up our security rules
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    public UserDetailsService userDetailsService() {
        return petUserDetailsService();
    }

}
