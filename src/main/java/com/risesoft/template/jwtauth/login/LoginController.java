package com.risesoft.template.jwtauth.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Basic auth방식으로 username:password를 받고 이것이 유효한지 확인후 유효하다면 jwt토큰 발행
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    @Autowired
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @RequestMapping
    public Map<String, String> getJwtByUsernamePassword(HttpServletRequest req) throws BadCredentialsException {
        try{
            final String header = req.getHeader("Authorization");
            if(header == null || !header.startsWith("Basic ")) throw new Exception("");

            String[] userpw = new String(Base64.getDecoder().decode(header.substring(6).getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8).split(":");

            if(!loginService.authenticateUsernamePassword(userpw[0], userpw[1])) throw new Exception("username password incorrect");

            String jwt = loginService.getJwtByUsername(userpw[0]);
            return Map.of("jwt", jwt);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            throw new BadCredentialsException(ex.getMessage());
        }
    }
}
