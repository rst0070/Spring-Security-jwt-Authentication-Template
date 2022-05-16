# Spring Security JWT Authentication Template
Spring Security의 JWT인증을 구현  
* login - `/login`에 `Basic auth`요청을 전달할시 jwt토큰을 발행한다.  
* `/common` - 유효한 JWT토큰을 Bearer token방식으로 전달해야 접근가능한 경로