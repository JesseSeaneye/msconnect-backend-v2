package com.msconnect.maintenancebackend.dto;

import lombok.Data;

@Data
public class ReportRequest {
    private Long userId;
    private String imageUrl;
    private String description;
    private Double latitude;
    private Double longitude;
    private String priority; // low, medium, high, urgent
}