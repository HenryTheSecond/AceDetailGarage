package com.garage.acedetails.security.jwt;

import com.auth0.jwt.JWT;
import com.garage.acedetails.security.model.CustomUserDetails;
import com.garage.acedetails.util.Constant;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;

public class JwtTokenProvider {

  public static String generateAccessToken(CustomUserDetails user, HttpServletRequest request) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + Constant.JWT_ACCESS_TOKEN_EXPIRATION);
    return JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(expiryDate)
        .withIssuer(request.getRequestURL().toString())
        .withClaim("roles",
            user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .sign(Constant.JWT_ALGORITHM);
  }


}
