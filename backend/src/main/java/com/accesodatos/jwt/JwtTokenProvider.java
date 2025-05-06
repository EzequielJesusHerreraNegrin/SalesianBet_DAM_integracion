package com.accesodatos.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.key.private}")
	private String privateKey;
}
