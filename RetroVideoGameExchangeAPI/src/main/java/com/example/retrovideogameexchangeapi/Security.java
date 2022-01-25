package com.example.retrovideogameexchangeapi;

import com.example.retrovideogameexchangeapi.repositories.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserJPARepository userJPA;

    static InMemoryUserDetailsManager memAuth = new InMemoryUserDetailsManager();

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("Configure - A");

        //Setting up Admin Role
        UserDetails newUser = User.withUsername("admin")
                .password(passEncode().encode("Password1234!"))
                .roles("ADMIN").build();

        memAuth.createUser(newUser);

        List<com.example.retrovideogameexchangeapi.models.User> userList = userJPA.findAll();
        for(com.example.retrovideogameexchangeapi.models.User user: userList) {
            UserDetails databaseUser = User.withUsername(user.getEmailAddress())
                    .password(passEncode().encode(user.getPassword()))
                    .roles("USER").build();

            memAuth.createUser(databaseUser);
        }

        auth.userDetailsService(memAuth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("Configure - B");

        //Block Certain HTTP Request by typing HTTP.<RequestMethod> in the antMatchers
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .antMatchers("/users/createUser").permitAll()
                .antMatchers(HttpMethod.GET, "/users/forgotPassword").permitAll()
                .antMatchers("/users/forgotPassword").hasRole("USER")
                .antMatchers("/users/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/offers/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/videoGames/**").hasAnyRole("ADMIN", "USER")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .httpBasic();
    }

    @Bean
    PasswordEncoder passEncode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager getInMemoryUserDetailsManager() {
        return memAuth;
    }
}
