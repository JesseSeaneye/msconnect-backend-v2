package com.msconnect.maintenancebackend.dto;

import com.msconnect.maintenancebackend.entity.Report;
import com.msconnect.maintenancebackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private Long id;
    private String userName;
    private String userEmail;
    private String imageUrl;
    private String description;
    private Double latitude;
    private Double longitude;
    private String status;
    private String priority;
    private String assignedToName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReportResponse fromEntity(Report report) {
        ReportResponse response = new ReportResponse();
        response.setId(report.getId());
        response.setUserName(report.getUser().getName());
        response.setUserEmail(report.getUser().getEmail());
        response.setImageUrl(report.getImageUrl());
        response.setDescription(report.getDescription());
        response.setLatitude(report.getLatitude());
        response.setLongitude(report.getLongitude());
        response.setStatus(report.getStatus());
        response.setPriority(report.getPriority());
        response.setCreatedAt(report.getCreatedAt());
        response.setUpdatedAt(report.getUpdatedAt());

        if (report.getAssignedTo() != null) {
            response.setAssignedToName(report.getAssignedTo().getName());
        }

        return response;
    }
}