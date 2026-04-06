package com.example.emergency.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.emergency.entity.Emergency;
import com.example.emergency.repository.EmergencyRepository;
import com.example.emergency.dto.EmergencyRequestDTO;

@Service
public class EmergencyService {

    private final EmergencyRepository repo;

    public EmergencyService(EmergencyRepository repo) {
        this.repo = repo;
    }

    public Emergency create(EmergencyRequestDTO dto) {

        Emergency e = new Emergency();
        e.setType(dto.getType());
        e.setDescription(dto.getDescription());
        e.setLocation(dto.getLocation());
        e.setCreatedBy(dto.getCreatedBy());

        // Priority logic
        if(dto.getType().equalsIgnoreCase("MEDICAL") || dto.getType().equalsIgnoreCase("SAFETY")) {
            e.setPriority("HIGH");
        } else {
            e.setPriority("MEDIUM");
        }

        e.setStatus("OPEN");

        return repo.save(e);
    }
    public Emergency acceptEmergency(Long id) {
    Emergency e = repo.findById(id)
        .orElseThrow(() -> new RuntimeException("Not found"));

    e.setStatus("ACCEPTED");
    return repo.save(e);
}

public Emergency resolveEmergency(Long id) {
    Emergency e = repo.findById(id)
        .orElseThrow(() -> new RuntimeException("Not found"));

    e.setStatus("RESOLVED");
    return repo.save(e);
}

    public List<Emergency> getAll() {
        return repo.findAll();
    }
}