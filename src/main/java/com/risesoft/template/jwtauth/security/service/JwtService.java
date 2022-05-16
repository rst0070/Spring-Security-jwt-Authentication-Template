package com.risesoft.template.jwtauth.security.service;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Jwt를 구성하거나 해석하는데 필요한 공통정보등을 모아둠
 */
@Service
public class JwtService {

    public static final int TOKEN_DURATION_MINUTES = 60;

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor("asdasdsadsadsadasdasdasdasdasdsadasd".getBytes(StandardCharsets.UTF_8));
    }

    public Date getExp(){
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c.add(Calendar.MINUTE, TOKEN_DURATION_MINUTES);
        return c.getTime();
    }

    public boolean isExpired(Date exp){
        return this.getDateNow().compareTo(exp) >= 0;
    }

    private Date getDateNow(){
        return Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime();
    }
}
