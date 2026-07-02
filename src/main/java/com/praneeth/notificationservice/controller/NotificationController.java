package com.praneeth.notificationservice.controller;

import com.praneeth.notificationservice.dto.EmailNotificationRequest;
import com.praneeth.notificationservice.dto.NotificationResponse;
import com.praneeth.notificationservice.dto.SmsNotificationRequest;
import com.praneeth.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes notification endpoints.
 *
 * <p>This controller is responsible only for accepting HTTP requests,
 * delegating work to the {@link NotificationService}, and returning
 * the appropriate HTTP response. It contains no business logic.</p>
 *
 * <p>Available endpoints:
 * <ul>
 *   <li>{@code POST /notifications/email} – send an email notification</li>
 *   <li>{@code POST /notifications/sms}   – send an SMS notification</li>
 * </ul>
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * Constructs the controller with the injected {@link NotificationService}.
     *
     * <p>Constructor injection is preferred over field injection because it makes
     * dependencies explicit and the class easier to unit test.</p>
     *
     * @param notificationService the service handling notification business logic
     */
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Accepts and processes an email notification request.
     *
     * <p>Example request body:
     * <pre>
     * {
     *   "recipientEmail": "user@example.com",
     *   "subject": "Welcome!",
     *   "messageBody": "Thank you for registering."
     * }
     * </pre>
     *
     * @param emailNotificationRequest the validated email notification payload
     * @return {@code 201 Created} with a {@link NotificationResponse} on success;
     *         {@code 400 Bad Request} if validation fails
     */
    @PostMapping("/email")
    public ResponseEntity<NotificationResponse> sendEmail(
            @Valid @RequestBody EmailNotificationRequest emailNotificationRequest) {
        NotificationResponse response = notificationService.sendEmail(emailNotificationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Accepts and processes an SMS notification request.
     *
     * <p>Example request body:
     * <pre>
     * {
     *   "recipientPhone": "+919876543210",
     *   "message": "Your OTP is 123456"
     * }
     * </pre>
     *
     * @param smsNotificationRequest the validated SMS notification payload
     * @return {@code 201 Created} with a {@link NotificationResponse} on success;
     *         {@code 400 Bad Request} if validation fails
     */
    @PostMapping("/sms")
    public ResponseEntity<NotificationResponse> sendSms(
            @Valid @RequestBody SmsNotificationRequest smsNotificationRequest) {
        NotificationResponse response = notificationService.sendSms(smsNotificationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
