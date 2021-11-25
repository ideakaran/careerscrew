package com.project.careerscrew.repository;

import com.project.careerscrew.entities.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.LOAD)
    Optional<UserEntity> findByEmail(String userEmail);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email " +
            "and u.verificationCodeExpiresAt >= UTC_TIMESTAMP and u.verificationCode = :verificationCode")
    Optional<UserEntity> verifyPasswordResetVerificationRequest(@Param("email") String email, @Param("verificationCode") String verificationCode);

    UserEntity findUserEntityById(Long id);

}
