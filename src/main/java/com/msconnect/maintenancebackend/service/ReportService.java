package com.msconnect.maintenancebackend.service;

import com.msconnect.maintenancebackend.entity.Report;
import com.msconnect.maintenancebackend.entity.User;
import com.msconnect.maintenancebackend.repository.ReportRepository;
import com.msconnect.maintenancebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public List<Report> getReportsByUser(Long userId) {
        return reportRepository.findByUserId(userId);
    }

    public List<Report> getReportsByStatus(String status) {
        return reportRepository.findByStatus(status);
    }

    public List<Report> getReportsByTechnician(Long technicianId) {
        return reportRepository.findByAssignedToId(technicianId);
    }

    public Report updateReportStatus(Long reportId, String status) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(status);
        return reportRepository.save(report);
    }

    public Report assignTechnician(Long reportId, Long technicianId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        User technician = userRepository.findById(technicianId)
                .orElseThrow(() -> new RuntimeException("Technician not found"));

        if (!"technician".equals(technician.getRole())) {
            throw new RuntimeException("User is not a technician");
        }

        report.setAssignedTo(technician);
        report.setStatus("in_progress");
        return reportRepository.save(report);
    }
public void deleteReport(Long id) {
    if (!reportRepository.existsById(id)) {
        throw new RuntimeException("Report not found with id: " + id);
    }
    reportRepository.deleteById(id);
}
public List<Report> getReportsByPriority(String priority) {
    return reportRepository.findByPriority(priority);
}
}
