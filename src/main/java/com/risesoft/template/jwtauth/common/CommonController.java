package com.risesoft.template.jwtauth.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 테스트를 위한 컨트롤러
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @RequestMapping
    public String common(){
        return "hello";
    }
}
