package com.zoho.backend.repository;

import com.zoho.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailEquals(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUserIdEquals(Long userId);

}
