package com.baoyun.ins.utils.token;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * token管理工具类
 * @ClassName: TokenUtil 
 * @Description: TODO
 * @author louis
 * @date 2018年3月29日 下午2:08:14
 */
public class TokenUtil {
	
	private final static String base64Secret = "dGhpc3lzand0cGFzc3dvcmQyMDE4";
    private final static int expiresSecond = 1200*60;//两小时
    private final static String issuer = "";//

    /**
     * 
     * @Description: TODO
     * @author louis
     * @date 2018年3月29日 下午3:15:59
     * @param token
     * @return
     * @throws
     */
    public static Claims get(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                .parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * 创建token
     * @Description: TODO
     * @author louis
     * @date 2018年3月29日 下午3:36:07
     * @param subject
     * @param scopes
     * @param privileges
     * @return
     * @throws
     */
    public static String createToken(String subject, Set<String> scopes, Set<String> privileges) {
    	
    	Optional.ofNullable(subject).orElseThrow(() -> new IllegalArgumentException("Cannot create Token without username"));
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", scopes);
        claims.put("privileges", privileges);
        LocalDateTime currentTime = LocalDateTime.now();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(expiresSecond)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, base64Secret)
                .compact();
        
        //生成JWT
        return token;
    }
    
}
