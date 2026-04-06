package com.example.emergency.repository;

import com.example.emergency.entity.Emergency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyRepository extends JpaRepository<Emergency, Long> {
}