package dasturlash.uz.util;

import dasturlash.uz.dto.JwtDTO;
import dasturlash.uz.enums.ProfileRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtil {

    private static final int tokenLifeTime = 3600 * 1000 * 24;
    private static final String secretKey = "itIsJustTestExampleJwLLLLLLLLLLLLLLLLLLLLLLLJJJJJJJJJJJJHHHHHHHHHHHHHHHHHHHHHHHt";

    public static String encode(String username, List<ProfileRole> role){

        String strRoles =role.stream().map(Enum::name).collect(Collectors.joining(","));

        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("role", strRoles);


        return Jwts
                .builder()
                .claims(map)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+tokenLifeTime))
                .signWith(getSignInKey())
                .compact();
    }


    public static JwtDTO decode(String token){
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String username = claims.getSubject();
        String role = claims.get("role").toString();

        List<ProfileRole> roleList = Arrays
                .stream(role.split(","))
                .map(ProfileRole::valueOf).toList();

        return new JwtDTO(username, roleList);
    }

    private static SecretKey getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);

    }
}
