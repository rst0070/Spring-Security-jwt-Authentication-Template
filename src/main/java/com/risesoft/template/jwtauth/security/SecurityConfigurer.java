package com.risesoft.template.jwtauth.security;

import com.risesoft.template.jwtauth.security.authentication.JwtAuthenticationProvider;
import com.risesoft.template.jwtauth.security.filter.JwtFilter;
import com.risesoft.template.jwtauth.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Autowired
    public SecurityConfigurer(
            UserDetailsService userDetailsService,
            JwtService jwtService
    ){
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                .antMatchers("/login**")
                .permitAll();
        http
                .antMatcher("/common**")
                .addFilterBefore(this.jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    public JwtFilter jwtFilter(){
        return new JwtFilter(this.authenticationManagerBean());
    }

    @Override
    public AuthenticationManager authenticationManagerBean(){
        return new ProviderManager(jwtAuthenticationProvider());
    }


    public AuthenticationProvider jwtAuthenticationProvider(){
        return new JwtAuthenticationProvider(userDetailsService, jwtService);
    }
}
