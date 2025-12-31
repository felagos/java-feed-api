package com.example.feed.repository;

import com.example.feed.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFolloweeId(Long followeeId);
    List<Follow> findByFollowerId(Long followerId);
    boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    void deleteByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
    long countByFollowerId(Long followerId);
    
    @Query("SELECT f.followerId FROM Follow f JOIN User u ON f.followerId = u.id WHERE f.followeeId = :followeeId AND u.lastLoginAt > :cutoffDate")
    List<Long> findActiveFollowerIds(@Param("followeeId") Long followeeId, @Param("cutoffDate") LocalDateTime cutoffDate);
}