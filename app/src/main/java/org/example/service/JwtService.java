package org.example.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service

public class JwtService {
    public static final String SECRET = "b7xfrYehxb++FrpC+vAdNELwYmqkcsqOcYVD54gPgkVdKElDrOFgYgYq/va141HmWUzkoPKfzMjpvGH+eJ0vwOe/I/JpvLXoFMgqtJ3oyeYAFdTA7J8SH5UvTkLCL6kUOcaXN3fkpMyY8XMevK4a28BnomaryQUkD4IVUNnBQ9+p+yang2AZrLZoBMNAwsw5LMkQK1XoCKZJevoFCjyWlS3CZXIHsv/Gm23Z03ape4dM8OCNjDkmY4c+fYVxGq10VDU0pYcsi8VRehexe34s1FAg7gfNLAO03c3Yxl3kaZmw7KLvbqojYcKZ8yNgSdFc72ZF0gHeFkSxay2v0RpvrnKSZ+5ox8l0/Y6a7oLOL4E=\n"

    public String extractUsername(String token){
        // here we got a token then we made a claim for the token
        return extractClaim(token, Claims::getSubject);
        // returns the JWT subject value or null  if not present
         }
         //T here means that we can pass any data type here

    public <T> T extractClaim(String token, Function<Claims , T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
         }

         //This method will call such a method which will retreive the claims
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
         }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
        //.before will return false if the token is not expired bby reading the date
    }

    //userdetail is the class which comes from the springboot which conatins username and password and everything
    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token))
    }
    // this will return true id the username is right and the token is not expired as well


    private String createToken(Map<String, Object> claims, String username){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*1))
                .signWith(getSignKey(), SignatureAlgorithm.ES256).compact();
    }


    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJwt(token)//gives the token to the parser and then makes the jwt for the parser
                .getBody();
                //chaining something i guess
         }

    private Key getSignKey(){
        byte[] keyBytes = Base64.Decoder.BASE64.decode(SECRET);
        //decoding the secret key then making a new key instance
        return Keys.hmacShaKeyFor(keyBytes);
         }

}
