package com.example.emergency.controller;

import com.example.emergency.entity.Emergency;
import com.example.emergency.repository.EmergencyRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/emergencies")
@CrossOrigin
public class EmergencyController {

    private final EmergencyRepository repo;

    public EmergencyController(EmergencyRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Emergency> getAll() {
        return repo.findAll();
    }

    private String getPriority(String type) {
        if (type.equalsIgnoreCase("fire") || type.equalsIgnoreCase("accident"))
            return "HIGH";
        else if (type.equalsIgnoreCase("blood"))
            return "MEDIUM";
        else
            return "LOW";
    }

    private String getCategory(String type) {
        if (type.equalsIgnoreCase("fire"))
            return "AUTHORITY";
        else
            return "COMMUNITY";
    }

    @PostMapping
    public Emergency create(@RequestBody Emergency e) {

        e.setStatus("PENDING");
        e.setPriority(getPriority(e.getType()));
        e.setCategory(getCategory(e.getType()));
        e.setCreatedAt(LocalDateTime.now());
        e.setEscalated(false);

        Emergency saved = repo.save(e);

        if (e.getCategory().equals("AUTHORITY")) {
            System.out.println("Fire emergency → Notifying Fire Department");
        } else {
            System.out.println("Community emergency → Notifying relevant users");
        }

        // ⏱️ WAIT TIME BASED ON PRIORITY
        int waitTime = switch (e.getPriority()) {
            case "HIGH" -> 30000;
            case "MEDIUM" -> 60000;
            default -> 90000;
        };

        // 🔥 ESCALATION THREAD
        new Thread(() -> {
            try {
                Thread.sleep(waitTime);

                Emergency updated = repo.findById(saved.getId()).orElse(null);

                if (updated != null && updated.getStatus().equals("PENDING")) {

                    updated.setEscalated(true);
                    repo.save(updated);

                    if (updated.getCategory().equals("AUTHORITY")) {
                        System.out.println("⚠️ Escalated to backup fire responders");
                    } else {
                        System.out.println("⚠️ Escalated to ALL community users");
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();

        return saved;
    }

    // 🔥 ACCEPT
    @PutMapping("/{id}/accept")
    public Emergency accept(@PathVariable Long id) {

        Emergency e = repo.findById(id).orElseThrow();

        if (!e.getStatus().equals("PENDING")) {
            throw new RuntimeException("Already handled");
        }

        e.setStatus("ACCEPTED");
        return repo.save(e);
    }

    // 🔥 RESOLVE
    @PutMapping("/{id}/resolve")
    public Emergency resolve(@PathVariable Long id) {

        Emergency e = repo.findById(id).orElseThrow();

        if (!e.getStatus().equals("ACCEPTED")) {
            throw new RuntimeException("Accept before resolving");
        }

        e.setStatus("RESOLVED");
        e.setResolvedAt(LocalDateTime.now());

        return repo.save(e);
    }
}