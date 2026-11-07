package ke.financial.ledger.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final byte[] secret;
  public JwtAuthenticationFilter(@Value("${security.jwt.secret}") String secret) {
    if (secret.getBytes(StandardCharsets.UTF_8).length < 32) throw new IllegalStateException("JWT_SECRET must be at least 32 bytes");
    this.secret=secret.getBytes(StandardCharsets.UTF_8);
  }
  @Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)throws ServletException,IOException {
    String header=request.getHeader("Authorization");
    if(header!=null&&header.startsWith("Bearer ")){
      try {
        var claims=Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret)).build().parseSignedClaims(header.substring(7)).getPayload();
        Object roles=claims.get("roles");
        List<SimpleGrantedAuthority> authorities=roles instanceof List<?> list?list.stream().map(Object::toString).map(SimpleGrantedAuthority::new).toList():List.of();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(claims.getSubject(),null,authorities));
      } catch (RuntimeException ignored) { response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); return; }
    }
    chain.doFilter(request,response);
  }
}
