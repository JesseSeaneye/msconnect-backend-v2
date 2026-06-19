package com.msconnect.maintenancebackend.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Double latitude;
    private Double longitude;
}