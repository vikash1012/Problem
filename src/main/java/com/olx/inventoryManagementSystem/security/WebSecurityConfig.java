package com.olx.inventoryManagementSystem.security;

import com.olx.inventoryManagementSystem.exceptions.ExcepetionHandlerFilter;
import com.olx.inventoryManagementSystem.filters.JwtRequestFilter;
import com.olx.inventoryManagementSystem.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    LoginUserService loginUserService;
    @Autowired
    JwtRequestFilter jwtRequestFilter;
    @Autowired
    ExcepetionHandlerFilter excepetionHandlerFilter;
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginUserService);
    }

    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/users/**").permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(excepetionHandlerFilter,UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtRequestFilter,ExcepetionHandlerFilter.class);

    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
