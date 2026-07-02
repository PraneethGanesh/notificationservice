package com.praneeth.notificationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SmsNotificationRequest {

    @NotBlank(message = "Recipient phone number must not be blank")
    @Pattern(
            regexp = "^\\+?[1-9]\\d{6,14}$",
            message = "Recipient phone number must be a valid international phone number (e.g. +919876543210)"
    )
    private String recipientPhone;

    @NotBlank(message = "Message must not be blank")
    @Size(max = 160, message = "SMS message must not exceed 160 characters")
    private String message;

    // ----- Constructors -----

    // Add no-args constructors to DTOs
    //Because you are not using Lombok, Jackson may fail to convert JSON into your DTOs.
    public SmsNotificationRequest() {
    }

    public SmsNotificationRequest(String recipientPhone, String message) {
        this.recipientPhone = recipientPhone;
        this.message = message;
    }

    // ----- Getters & Setters -----

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SmsNotificationRequest{" +
                "recipientPhone='" + recipientPhone + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
