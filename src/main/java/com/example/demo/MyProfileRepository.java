package com.example.demo;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called MyProfileRepository
public interface MyProfileRepository extends CrudRepository<User, Integer> {

    User findByName(String username);
}