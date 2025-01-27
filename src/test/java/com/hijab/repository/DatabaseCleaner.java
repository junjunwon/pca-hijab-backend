package com.hijab.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DatabaseCleaner {  

    private final JdbcTemplate jdbcTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public DatabaseCleaner(JdbcTemplate jdbcTemplate, RedisTemplate<String, String> redisTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
        this.redisTemplate = redisTemplate;
    }  

    public void cleanDatabase() {  
        // 외래 키 제약 조건을 일시적으로 비활성화 (H2 데이터베이스 예시)  
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");  

        // 모든 테이블 이름 조회  
        List<String> tableNames = jdbcTemplate.queryForList(
                "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='PUBLIC'", String.class);  

        // 각 테이블의 데이터 삭제  
        tableNames.forEach(tableName ->  
                jdbcTemplate.execute("TRUNCATE TABLE " + tableName));  

        // 외래 키 제약 조건을 다시 활성화  
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");  
    }  
    
    public void cleanRedis() {
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null && !keys.isEmpty()) {  
            redisTemplate.delete(keys);  
        }  
    }
}