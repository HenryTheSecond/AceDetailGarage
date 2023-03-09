package com.garage.acedetails.security.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garage.acedetails.constants.ApplicationConstants;
import com.garage.acedetails.dto.LoginRequest;
import com.garage.acedetails.dto.LoginResponse;
import com.garage.acedetails.model.DataResponse;
import com.garage.acedetails.security.jwt.JwtTokenProvider;
import com.garage.acedetails.security.model.CustomUserDetails;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

@Slf4j
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    throws AuthenticationException {
    ObjectMapper objectMapper = new ObjectMapper();
    LoginRequest loginRequest;
    String errorMessage = "";
    response.setContentType(APPLICATION_JSON_VALUE);
    try {
      loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
      String username = loginRequest.getUsername();
      String password = loginRequest.getPassword();
      if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
        UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
      } else {
        errorMessage = ApplicationConstants.USERNAME_OR_PASSWORD_MISSING;
      }
    } catch (DisabledException ex) {
      errorMessage = ApplicationConstants.ACCOUNT_INACTIVE;
    } catch (BadCredentialsException ex) {
      errorMessage = ApplicationConstants.USERNAME_OR_PASSWORD_INCORRECT;
    } catch (IOException ex) {
      errorMessage = ApplicationConstants.UNEXPECTED_ERROR;
      log.error("Error logging in: {}", ex.getMessage());
    }

    try {
      objectMapper.writeValue(
        response.getOutputStream(),
        new DataResponse(ApplicationConstants.UNAUTHORIZED, errorMessage, ApplicationConstants.UNAUTHORIZED_CODE)
      );
    } catch (IOException ex) {
      log.error("Error logging in: {}", ex.getMessage());
    }
    return null;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
    Authentication authResult) {
    try {
      CustomUserDetails user = (CustomUserDetails) authResult.getPrincipal();
      String accessToken = JwtTokenProvider.generateAccessToken(user, request);
      LoginResponse loginResponse = new LoginResponse(accessToken);
      new ObjectMapper().writeValue(response.getOutputStream(), new DataResponse(loginResponse));
    } catch (IOException ex) {
      log.error("Error logging in: {}", ex.getMessage());
    }
  }
}
