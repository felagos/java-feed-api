package com.example.feed.repository;

import com.example.feed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u FROM User u WHERE u.lastLoginAt > :cutoffDate")
    List<User> findUsersActiveAfter(@Param("cutoffDate") LocalDateTime cutoffDate);
    
    @Query("SELECT u.id FROM User u WHERE u.lastLoginAt > :cutoffDate")
    List<Long> findActiveUserIdsAfter(@Param("cutoffDate") LocalDateTime cutoffDate);
    
    @Query("SELECT u FROM User u WHERE u.id IN :userIds AND u.lastLoginAt > :cutoffDate")
    List<User> findActiveUsersInList(@Param("userIds") List<Long> userIds, @Param("cutoffDate") LocalDateTime cutoffDate);
    
    @Query("SELECT u.id FROM User u WHERE u.id IN :userIds AND u.lastLoginAt > :cutoffDate")
    List<Long> findActiveUserIdsInList(@Param("userIds") List<Long> userIds, @Param("cutoffDate") LocalDateTime cutoffDate);
}