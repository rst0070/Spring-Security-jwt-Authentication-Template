package com.risesoft.template.jwtauth.login;

import com.risesoft.template.jwtauth.security.service.JwtService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import com.risesoft.template.jwtauth.security.userdetails.CustomUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Autowired
    public LoginService(
            CustomUserDetailsService userDetailsService,
            JwtService jwtService
    ){
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    /**
     * 해당하는 username, password의 사용자가 있는지(혹은 정보가 일치하는지) 확인
     * @param username
     * @param password
     * @return
     */
    public boolean authenticateUsernamePassword(String username, String password){
        try{
            return userDetailsService.loadUserByUsername(username).getPassword().equals(password);
        }catch(Exception ex){
            return false;
        }
    }

    /**
     * username이 sub 클레임으로 작성된 jwt반환
     * @param username
     * @return jwt문자열
     */
    public String getJwtByUsername(String username){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(jwtService.getExp())
                .signWith(jwtService.getSecretKey())
                .compact();
    }
}
