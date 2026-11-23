package com.example.luggar.user;

import com.example.luggar.common.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    default User findUserOrThrow(Long id){
        return findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id " +id + " not found."));
    }
}
