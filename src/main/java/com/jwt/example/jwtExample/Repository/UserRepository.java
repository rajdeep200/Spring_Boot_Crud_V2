package com.jwt.example.jwtExample.Repository;
import com.jwt.example.jwtExample.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
