package com.risesoft.template.jwtauth.security.authentication;

import com.risesoft.template.jwtauth.security.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * JwtAuthentication객체를 받아서 해당 객체가 포함하는 JWT정보를 검사한 후
 * 유효한 정보라면 인증이 완료되어 제대로 구성된 JwtAuthentication객체를 전달한다.
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public JwtAuthenticationProvider(
            UserDetailsService userDetailsService,
            JwtService jwtService
    ){
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;

    }

    /**
     * @param authentication - 아직 인증이 완료되지 않은 JwtAuthentication객체
     * @return Authentication - 완전히 인증된 Authentication이 전달된다.
     * @throws AuthenticationException - authentication에 실패할경우 발생
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            if(!(authentication instanceof JwtAuthentication)) throw new InternalAuthenticationServiceException("Authentication is not instance of JwtAuthentication");

            String jwt = authentication.getCredentials().toString();

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtService.getSecretKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            if(jwtService.isExpired(claims.getExpiration())) throw new BadCredentialsException("jwt expired");

            String username = claims.getSubject();
            ((JwtAuthentication) authentication)
                    .setUserDetails(userDetailsService.loadUserByUsername(username));
            authentication.setAuthenticated(true);

        }catch(Exception ex){
            logger.warn(ex.getMessage());
            throw new BadCredentialsException(ex.getMessage());
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }
}
