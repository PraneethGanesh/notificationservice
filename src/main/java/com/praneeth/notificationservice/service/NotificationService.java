package com.praneeth.notificationservice.service;

import com.praneeth.notificationservice.dto.EmailNotificationRequest;
import com.praneeth.notificationservice.dto.NotificationResponse;
import com.praneeth.notificationservice.dto.SmsNotificationRequest;

/**
 * Service interface defining the contract for all notification operations.
 *
 * <p>Separating the interface from the implementation allows:
 * <ul>
 *   <li>Easy unit testing via mocking (Mockito can mock this interface directly)</li>
 *   <li>Swapping implementations without changing the controller (e.g., switching
 *       from a simulated service to a real SMTP/SMS gateway in later phases)</li>
 *   <li>Following the Dependency Inversion Principle – the controller depends on
 *       the abstraction, not the concrete class</li>
 * </ul>
 */
public interface NotificationService {

    /**
     * Sends an email notification based on the provided request.
     *
     * @param emailNotificationRequest the validated email notification request
     *                                 containing recipient, subject, and message body
     * @return a {@link NotificationResponse} indicating success or failure,
     *         including the recipient and a descriptive message
     */
    NotificationResponse sendEmail(EmailNotificationRequest emailNotificationRequest);

    /**
     * Sends an SMS notification based on the provided request.
     *
     * @param smsNotificationRequest the validated SMS notification request
     *                               containing recipient phone number and message
     * @return a {@link NotificationResponse} indicating success or failure,
     *         including the recipient and a descriptive message
     */
    NotificationResponse sendSms(SmsNotificationRequest smsNotificationRequest);
}