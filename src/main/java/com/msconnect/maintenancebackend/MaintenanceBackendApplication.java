package com.msconnect.maintenancebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MaintenanceBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MaintenanceBackendApplication.class, args);
        System.out.println("✅ MS Connect Backend is running!");
    }
}