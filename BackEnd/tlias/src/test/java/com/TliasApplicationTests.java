package com;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
class TliasApplicationTests {

    @Test
    void testGenJwt() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("name", "tom");

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "itheima")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour
                .compact();
        System.out.println(jwt);
    }

    @Test
    public void testParseJwt() {
        Claims itheima = Jwts.parser()
                .setSigningKey("itheima")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidG9tIiwiaWQiOjEsImV4cCI6MTcxNzM2MjM0MX0.Lnrhqc2COvfNQXfilP6m_RRoxwpkU12TJD64HsILvo0")
                .getBody();
        System.out.println(itheima);

    }

}
