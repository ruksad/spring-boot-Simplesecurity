package com.security.learn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by mohammad on 25/3/17.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  CustomSuccessHandler customSuccessHandler;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("USER");
    auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ADMIN");
    auth.inMemoryAuthentication().withUser("dba").password("root123").roles("ADMIN", "DBA");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/", "/home").access("hasRole('USER')")
        .antMatchers("/admin").access("hasRole('ADMIN')")
        .antMatchers("/admin/**").access("hasRole('ADMIN')")
        .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
        .and().formLogin().loginPage("/login").usernameParameter("ssoId")
        .passwordParameter("password").successHandler(customSuccessHandler)
        .and().csrf()
        .and().exceptionHandling().accessDeniedPage("/Access_denied");
  }

}
