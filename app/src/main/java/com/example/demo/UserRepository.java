package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT id FROM user WHERE (email = :email AND password = :password)", nativeQuery = true)
    public Long authenticate(String email, String password);
}
