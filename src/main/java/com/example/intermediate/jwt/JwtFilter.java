package com.example.intermediate.jwt;

import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.security.Key;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  public static String AUTHORIZATION_HEADER = "Authorization";
  public static String BEARER_PREFIX = "Bearer ";

  public static String AUTHORITIES_KEY = "auth";

  private final String SECRET_KEY;

  private final TokenProvider tokenProvider;
  private final UserDetailsServiceImpl userDetailsService;

  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    Key key = Keys.hmacShaKeyFor(keyBytes);

    String jwt = resolveToken(request);

    if (StringUtils.hasText(jwt) && tokenProvider.valipassengerToken(jwt)) {
      Claims claims;
      try {
        claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody(); //서명 검증, 예외처리
      } catch (ExpiredJwtException e) {
        claims = e.getClaims();
      }

      if (claims.getExpiration().toInstant().toEpochMilli() < Instant.now().toEpochMilli()) { //현재시간 타임스탬프 유효성 검증
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(
            new ObjectMapper().writeValueAsString(
                ResponseDto.fail("BAD_REQUEST", "Token이 유효햐지 않습니다.")
            )
        );
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      }

      String subject = claims.getSubject(); //클레임에서 권한정보 꺼내오기
      Collection<? extends GrantedAuthority> authorities =
          Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());

      UserDetails principal = userDetailsService.loadUserByUsername(subject); //인증 객체

      Authentication authentication = new UsernamePasswordAuthenticationToken(principal, jwt, authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }
  private String resolveToken(HttpServletRequest request) { //헤더에서 토큰정보 꺼내오기
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

}