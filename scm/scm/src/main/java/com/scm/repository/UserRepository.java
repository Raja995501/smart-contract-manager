package com.scm.repository;
import java.util.Optional;
import com.scm.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
  // Import your User entity class

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByEmail(String email); 
    // You can define custom query methods here if needed
}
