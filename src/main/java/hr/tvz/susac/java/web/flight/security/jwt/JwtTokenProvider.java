package hr.tvz.susac.java.web.flight.security.jwt;

import hr.tvz.susac.java.web.flight.security.UserDetails;
import hr.tvz.susac.java.web.flight.security.UserDetailsImpl;
import hr.tvz.susac.java.web.flight.service.AirApiService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class JwtTokenProvider
{
    @Value("${jwt.token.expiration}")
    private Long expiration;

    @Value("${jwt.token.secret}")
    private String secret;

    private final AirApiService airApiService;
    private final UserDetailsImpl userDetailsImpl;

    public JwtTokenProvider(AirApiService airApiService, UserDetailsImpl userDetailsImpl) {
        this.airApiService = airApiService;
        this.userDetailsImpl = userDetailsImpl;
    }

    public Authentication getAuthentication(String token)
    {
        UserDetails userDetails = userDetailsImpl.loadUserByUsername(getUsername(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String generateToken(Authentication authentication)
    {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getUsername());
        claims.put("created", new Date());
        claims.put("air", airApiService.requestToken());

        return generateToken(claims);
    }

    public String generateToken(Map<String, Object> claims)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String extractAirApiTokenFromJwt(String jwt)
    {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();

        } catch (Exception e) {
            log.error("Could not get all claims Token from passed token");
            claims = null;
        }

        return claims.get("air", String.class);
    }

    public String resolveToken(HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");

        if (!Objects.isNull(bearerToken) && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);

        return null;
    }

    public boolean validateToken(String token)
    {
        try
        {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }
        catch (JwtException | IllegalArgumentException ex)
        {
            log.debug("Invalid JWT token");
        }
        return false;
    }

    public String getUsername(String token)
    {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    private Date generateExpirationDate()
    {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
