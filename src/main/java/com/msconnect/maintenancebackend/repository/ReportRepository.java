package com.msconnect.maintenancebackend.repository;

import com.msconnect.maintenancebackend.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByUserId(Long userId);
    List<Report> findByStatus(String status);
    List<Report> findByAssignedToId(Long technicianId);
    List<Report> findByPriority(String priority);
    
}