package com.praneeth.notificationservice.dto;

import java.time.LocalDateTime;

public class NotificationResponse {

    private boolean success;
    private String message;
    private String notificationType;   // "EMAIL" or "SMS"
    private String recipient;
    private LocalDateTime timestamp;

    // ----- Constructors -----

    public NotificationResponse(boolean success, String message, String notificationType, String recipient) {
        this.success = success;
        this.message = message;
        this.notificationType = notificationType;
        this.recipient = recipient;
        this.timestamp = LocalDateTime.now();
    }

    // ----- Static factory helpers -----

    public static NotificationResponse success(String notificationType, String recipient) {
        return new NotificationResponse(
                true,
                notificationType + " notification sent successfully to " + recipient,
                notificationType,
                recipient
        );
    }

    public static NotificationResponse failure(String notificationType, String recipient, String reason) {
        return new NotificationResponse(
                false,
                "Failed to send " + notificationType + " notification to " + recipient + ": " + reason,
                notificationType,
                recipient
        );
    }

    // ----- Getters & Setters -----

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
