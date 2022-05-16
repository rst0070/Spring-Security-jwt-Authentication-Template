package com.risesoft.template.jwtauth.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 현재 SecurityContext의 인증여부를 나타내는 객체
 * Jwt정보로 구성된다.
 */
public class JwtAuthentication implements Authentication {

    private final String JWT_STRING;
    private boolean authenticated;
    private UserDetails userDetails;

    public JwtAuthentication(String jwtStr){
        authenticated = false;
        this.JWT_STRING = jwtStr;
    }

    public void setUserDetails(UserDetails userDetails){
        this.userDetails = userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userDetails.getAuthorities();
    }

    /**
     *
     * @return String - jwt 문자열 자체를 가져온다.
     */
    @Override
    public String getCredentials() {
        return this.JWT_STRING;
    }

    /**
     *
     * @return UserDetails - 해당 인증정보와 연결된 사용자의 정보를 가져온다.
     */
    @Override
    public UserDetails getDetails() {
        return this.userDetails;
    }

    /**
     * @return String - 연결된 사용자의 id를 반환한다.
     */
    @Override
    public String getPrincipal() {
        return this.userDetails.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return getPrincipal();
    }
}
