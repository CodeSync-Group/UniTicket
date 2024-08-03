package com.codesync.uniticket.repositories;

import com.codesync.uniticket.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByProfileId(Long profileId);

    Boolean existsByUsername(String username);
}
