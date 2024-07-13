package com.spotify.repository;

import com.spotify.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query("select u from Users u where u.username =:username")
    Users getByUsername(String username);
}
