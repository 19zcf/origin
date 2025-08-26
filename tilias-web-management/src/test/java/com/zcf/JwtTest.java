package com.zcf;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    /**
     * 生成token
     */
    @Test
    public  void testGenerateToken() {

        Map<String, Object> dataMap=new HashMap<>();
        dataMap.put("id", 1);
        dataMap.put("username", "admin");
        dataMap.put("password", "123456");
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, "secret")//签名算法
                .addClaims(dataMap)//添加自定义属性
                .setExpiration(new Date(System.currentTimeMillis() + 3600*1000))//设置过期时间
                .compact();

        System.out.println( token);
    }


}
