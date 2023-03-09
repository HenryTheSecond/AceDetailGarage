package com.garage.acedetails.security.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.util.Constant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    boolean isIndentifed = false; // if jwt is valid, then set isIndentified true
    String errorMessage = "";
    if (request.getServletPath().equals("/login") || request.getServletPath().equals("/goods")) {
      filterChain.doFilter(request, response);
    } else {
      String authorizationHeader = request.getHeader(AUTHORIZATION);
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        try {
          String token = authorizationHeader.substring("Bearer ".length());
          JWTVerifier verifier = JWT.require(Constant.JWT_ALGORITHM).build();
          DecodedJWT decodedJWT = verifier.verify(token);
          String username = decodedJWT.getSubject();
          String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
          Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
          Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
          UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, null, authorities);
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          filterChain.doFilter(request, response);
          isIndentifed = true;
        } catch (Exception ex) {
          log.error("Error logging in: {}", ex.getMessage());
          errorMessage = ex.getMessage();
        }
      } else {
        errorMessage = ApplicationConstants.JWT_TOKEN_MISSING;
      }
      if (!isIndentifed) {
        response.setStatus(ApplicationConstants.FORBIDDEN_CODE);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(
          response.getOutputStream(),
          new DataResponse(
            ApplicationConstants.FORBIDDEN,
            errorMessage,
            ApplicationConstants.FORBIDDEN_CODE
          )
        );
      }
    }
  }
}
