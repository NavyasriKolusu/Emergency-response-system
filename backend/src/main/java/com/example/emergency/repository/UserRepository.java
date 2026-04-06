package com.example.emergency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.emergency.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}