package com.msconnect.maintenancebackend.controller;

import com.msconnect.maintenancebackend.dto.ReportRequest;
import com.msconnect.maintenancebackend.dto.ReportResponse;
import com.msconnect.maintenancebackend.entity.Report;
import com.msconnect.maintenancebackend.entity.User;
import com.msconnect.maintenancebackend.service.ReportService;
import com.msconnect.maintenancebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody ReportRequest request) {
        try {
            User user = userService.getUserById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Report report = new Report();
            report.setUser(user);
            report.setImageUrl(request.getImageUrl());
            report.setDescription(request.getDescription());
            report.setLatitude(request.getLatitude());
            report.setLongitude(request.getLongitude());

            if (request.getPriority() != null) {
                report.setPriority(request.getPriority());
            }

            Report savedReport = reportService.createReport(report);
            return ResponseEntity.ok(ReportResponse.fromEntity(savedReport));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports.stream()
                .map(ReportResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable Long id) {
        return reportService.getReportById(id)
                .map(report -> ResponseEntity.ok(ReportResponse.fromEntity(report)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReportResponse>> getReportsByUser(@PathVariable Long userId) {
        List<Report> reports = reportService.getReportsByUser(userId);
        return ResponseEntity.ok(reports.stream()
                .map(ReportResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReportResponse>> getReportsByStatus(@PathVariable String status) {
        List<Report> reports = reportService.getReportsByStatus(status);
        return ResponseEntity.ok(reports.stream()
                .map(ReportResponse::fromEntity)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateReportStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String status = request.get("status");
            Report updated = reportService.updateReportStatus(id, status);
            return ResponseEntity.ok(ReportResponse.fromEntity(updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{id}/assign")
    public ResponseEntity<?> assignTechnician(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        try {
            Long technicianId = request.get("technicianId");
            Report updated = reportService.assignTechnician(id, technicianId);
            return ResponseEntity.ok(ReportResponse.fromEntity(updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/technicians")
    public ResponseEntity<?> getTechnicians() {
        return ResponseEntity.ok(userService.getTechnicians());
    }
    @GetMapping("/technician/{technicianId}")
public ResponseEntity<List<ReportResponse>> getReportsByTechnician(@PathVariable Long technicianId) {
    List<Report> reports = reportService.getReportsByTechnician(technicianId);
    return ResponseEntity.ok(reports.stream()
            .map(ReportResponse::fromEntity)
            .collect(Collectors.toList()));
}
@GetMapping("/stats")
public ResponseEntity<Map<String, Object>> getStats() {
    Map<String, Object> stats = new HashMap<>();
    
    List<Report> allReports = reportService.getAllReports();
    stats.put("totalReports", allReports.size());
    stats.put("pendingReports", reportService.getReportsByStatus("pending").size());
    stats.put("inProgressReports", reportService.getReportsByStatus("in_progress").size());
    stats.put("resolvedReports", reportService.getReportsByStatus("resolved").size());
    stats.put("totalTechnicians", userService.getTechnicians().size());
    
    // Optional: Get reports by priority
    stats.put("highPriority", reportService.getReportsByPriority("high").size());
    stats.put("mediumPriority", reportService.getReportsByPriority("medium").size());
    stats.put("lowPriority", reportService.getReportsByPriority("low").size());
    stats.put("urgentPriority", reportService.getReportsByPriority("urgent").size());
    
    return ResponseEntity.ok(stats);
}
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteReport(@PathVariable Long id) {
    try {
        reportService.deleteReport(id);
        return ResponseEntity.ok(Map.of("message", "Report deleted successfully"));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
}
}

