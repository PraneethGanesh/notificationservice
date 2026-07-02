package com.praneeth.notificationservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * REST controller exposing lightweight health and version endpoints.
 *
 * <p>These endpoints provide a simplified, application-level status check.
 * Spring Boot Actuator also exposes {@code /actuator/health} automatically,
 * which is the standard endpoint used by Docker health checks and Kubernetes
 * liveness/readiness probes in later phases.</p>
 *
 * <p>Available endpoints:
 * <ul>
 *   <li>{@code GET /health}  – returns current application status</li>
 *   <li>{@code GET /version} – returns application name and version</li>
 * </ul>
 */
@RestController
public class HealthController {

    @Value("${APP_VERSION:1.0.0}")
    private String appVersion;

    @Value("${spring.application.name:Notification Service}")
    private String appName;
    @Value("${SERVER_PORT: 8100}")
    private String serverPort;

    /**
     * Returns the current health status of the application.
     *
     * <p>Always returns {@code "status": "UP"} in Phase 1. Future phases will
     * include database and message broker connectivity checks.</p>
     *
     * @return {@code 200 OK} with a JSON body containing status, application name,
     *         and current timestamp
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "UP");
        response.put("application", appName);
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    /**
     * Returns the current application version details.
     *
     * @return {@code 200 OK} with a JSON body containing application name,
     *         version, current phase, and timestamp
     */
    @GetMapping("/version")
    public ResponseEntity<Map<String, Object>> version() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("application", appName);
        response.put("version", appVersion);
        response.put("Server Port",serverPort);
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }
}
