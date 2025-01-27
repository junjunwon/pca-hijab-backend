package com.hijab.repository.jpa;

import com.hijab.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> existsByNickname(String nickname);
    Optional<User> existsByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);
}
