package app.moz.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "4171ACC6E95C3187C51614D42C1CA22D814D67B0F0ECC7DCED8FDF28C28751E8";

    public String extractUsername(String token) {
        return  extractSingleClaim(token, Claims::getSubject);
    }

    public <T> T extractSingleClaim (String token, Function<Claims, T> claimsTFunction) {

        final Claims claims = extractClaims(token);

        return claimsTFunction.apply(claims);
    }

    //generate token with extraclaims and userDetails

    public String generateToken (
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60 * 60 * 24 ) )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

   //generate token from only userDetails
    public String generateToken (UserDetails userDetails)
    {
        return generateToken(new HashMap<>(), userDetails);
    }

    //check if token is valid

    public boolean isTokenValid ( String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }

    public boolean isTokenExpired ( String token ) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration ( String token) {
        return  extractSingleClaim(token, Claims::getExpiration);
    }


    public Claims extractClaims(String token) {

        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Key getSigningKey () {
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
