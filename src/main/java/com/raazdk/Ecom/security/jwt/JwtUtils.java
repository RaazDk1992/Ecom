package com.raazdk.Ecom.security.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;


@Component
public class JwtUtils {

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;
    @Value("${spring.app.jwtExpirationMs}")
    private String jwtExpiration;

    private final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public String getJwtFromHeader(HttpServletRequest request){
        String jwt = request.getHeader("Authorization");
        if(jwt !=null && jwt.startsWith("Bearer")){
            return  jwt.substring(7);
        }

        return null;
    }


    public  boolean validateJwt(String token){

            try{
                Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token);
                return  true;
            }catch (MalformedJwtException ex){

                logger.error("Malformed JWT token: {}", ex.getMessage());
            } catch (ExpiredJwtException ex){

                logger.error("Expired JWT token: {}", ex.getMessage());

            }catch (UnsupportedJwtException ex){
                logger.error("Unsupported JWT token: {}", ex.getMessage());


            }catch (IllegalArgumentException ex){

                logger.error("IllegalArgument JWT token: {}", ex.getMessage());


            }

            return  false;

    }

    public String generateTokenFromUsername(UserDetails userDetails){

        Date issueddate = new Date();
        Date expirationdate = Date.from(issueddate.toInstant().plusMillis(Long.parseLong(jwtExpiration)));
        String username = userDetails.getUsername();
        String token = Jwts.builder().
                subject(username)
                .issuedAt(issueddate)
                .expiration(expirationdate)
                .signWith(key())
                .compact();
        return  token;

    }

    public String getUsernameFromToken(String token){
        String username = Jwts.parser().verifyWith((SecretKey) key()).
                build().parseSignedClaims(token).getPayload().getSubject();

        return  username;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));}

    }
