package com.example.retrovideogameexchangeapi.configuration;

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
        for (com.example.retrovideogameexchangeapi.models.User user : userList) {
            UserDetails databaseUser = User.withUsername(user.getEmailAddress())
                    .password(user.getPassword())
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
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/users/forgotPassword").permitAll()
                .antMatchers("/users/forgotPassword").hasRole("USER")
                .antMatchers("/users/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers("/users/forgotPassword").hasRole("USER")
                .antMatchers("/users/updateName").hasRole("USER")
                .antMatchers("/users/updateAddress").hasRole("USER")
                .antMatchers("/users/changePassword").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/offers").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/offers").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/offers/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE,"/offers/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/offers/{id}").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/videoGames").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/videoGames").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/videoGames/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/videoGames/{id}").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH, "/videoGames/{id}").hasRole("USER")
                .antMatchers("/users/{id}/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/videoGames/{id}/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/offers/{id}/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/admin/emailEvent").hasRole("ADMIN")
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