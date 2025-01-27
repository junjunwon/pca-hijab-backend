package com.hijab.repository;

import com.hijab.data.UserTestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertUser(String email, String password, String nickname, String role) {
        String sql = "INSERT INTO USERS (email, password, nickname, role) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, email, password, nickname, role);
    }

    public UserTestVO findUser(String email) {
        try {
            String sql = "SELECT user_id, email, password, nickname, role, created_at as createdAt, updated_at as updatedAt FROM USERS where email = ?";
            UserTestVO utvo = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(UserTestVO.class), email);
            return utvo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public UserTestVO findUserById(String userId) {
        try {
            String sql = "SELECT user_id, email, password, nickname, role, created_at as createdAt, updated_at as updatedAt FROM USERS where user_id = ?";
            UserTestVO utvo = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(UserTestVO.class), userId);
            return utvo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void changeRoleById(String userId, String role) {
        String sql = "UPDATE USERS SET role=? WHERE user_id=?";
        jdbcTemplate.update(sql, role, userId);
    }

}