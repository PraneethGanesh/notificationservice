package com.praneeth.notificationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmailNotificationRequest {

    @NotBlank(message = "Recipient email must not be blank")
    @Email(message = "Recipient email must be a valid email address")
    private String recipientEmail;

    @NotBlank(message = "Subject must not be blank")
    @Size(max = 255, message = "Subject must not exceed 255 characters")
    private String subject;

    @NotBlank(message = "Message body must not be blank")
    @Size(max = 5000, message = "Message body must not exceed 5000 characters")
    private String messageBody;

    // ----- Constructors -----

    // Add no-args constructors to DTOs
    //Because you are not using Lombok, Jackson may fail to convert JSON into your DTOs.

    public EmailNotificationRequest() {
    }

    public EmailNotificationRequest(String recipientEmail, String subject, String messageBody) {
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.messageBody = messageBody;
    }

    // ----- Getters & Setters -----

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return "EmailNotificationRequest{" +
                "recipientEmail='" + recipientEmail + '\'' +
                ", subject='" + subject + '\'' +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }
}
