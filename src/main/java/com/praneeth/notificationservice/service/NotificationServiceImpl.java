package com.praneeth.notificationservice.service;

import com.praneeth.notificationservice.dto.EmailNotificationRequest;
import com.praneeth.notificationservice.dto.NotificationResponse;
import com.praneeth.notificationservice.dto.SmsNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link NotificationService}.
 *
 * <p>Phase 1: Simulates email and SMS delivery by logging the request details.
 * No actual messages are sent in this phase.</p>
 *
 * <p>Future phases will replace or extend this implementation:
 * <ul>
 *   <li>Phase 7 – persist each notification to a MySQL database</li>
 *   <li>Phase 8 – publish notifications to Kafka or RabbitMQ for async delivery</li>
 * </ul>
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    /**
     * {@inheritDoc}
     *
     * <p>Phase 1 behaviour: logs the request and returns a success response.
     * No real email is sent.</p>
     */
    @Override
    public NotificationResponse sendEmail(EmailNotificationRequest emailNotificationRequest) {
        log.info("Processing EMAIL notification → recipient={}, subject={}",
                emailNotificationRequest.getRecipientEmail(),
                emailNotificationRequest.getSubject());

        // TODO Phase 7: persist notification record to DB
        // TODO Phase 8: publish to Kafka/RabbitMQ topic for async delivery

        log.info("EMAIL notification simulated successfully for {}",
                emailNotificationRequest.getRecipientEmail());

        return NotificationResponse.success("EMAIL", emailNotificationRequest.getRecipientEmail());
    }

    /**
     * {@inheritDoc}
     *
     * <p>Phase 1 behaviour: logs the request and returns a success response.
     * No real SMS is sent.</p>
     */
    @Override
    public NotificationResponse sendSms(SmsNotificationRequest smsNotificationRequest) {
        log.info("Processing SMS notification → recipient={}",
                smsNotificationRequest.getRecipientPhone());

        // TODO Phase 7: persist notification record to DB
        // TODO Phase 8: publish to Kafka/RabbitMQ topic for async delivery

        log.info("SMS notification simulated successfully for {}",
                smsNotificationRequest.getRecipientPhone());

        return NotificationResponse.success("SMS", smsNotificationRequest.getRecipientPhone());
    }
}

