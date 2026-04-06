package com.example.emergency.dto;

import jakarta.validation.constraints.NotBlank;

public class EmergencyRequestDTO {

    @NotBlank
    private String type;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    private Long createdBy;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}