package com.praneeth.notificationservice.service;

import com.praneeth.notificationservice.dto.EmailNotificationRequest;
import com.praneeth.notificationservice.dto.NotificationResponse;
import com.praneeth.notificationservice.dto.SmsNotificationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceImplTest {

    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        notificationService = new NotificationServiceImpl();
    }

    @Test
    void testSendEmail() {
        //Arrange
        EmailNotificationRequest emailNotificationRequest = new EmailNotificationRequest();
        emailNotificationRequest.setRecipientEmail("user@gmail.com");
        emailNotificationRequest.setSubject("Test Subject");
        emailNotificationRequest.setMessageBody("Message Body");
        //Act
        NotificationResponse notificationResponse =notificationService.sendEmail(emailNotificationRequest);
        //Assert
        assertNotNull(notificationResponse);
        assertTrue(notificationResponse.isSuccess());
        assertEquals("EMAIL", notificationResponse.getNotificationType());
        assertEquals("user@gmail.com",notificationResponse.getRecipient());
        assertEquals("EMAIL notification sent successfully to user@gmail.com",notificationResponse.getMessage());
    }

    @Test
    void sendSmsNotification(){
        //Arrange
        SmsNotificationRequest smsNotificationRequest = new SmsNotificationRequest();
        smsNotificationRequest.setRecipientPhone("+918105270476");
        smsNotificationRequest.setMessage("Test Message");

        //Act
        NotificationResponse notificationResponse =notificationService.sendSms(smsNotificationRequest);

        //Assert
        assertNotNull(notificationResponse);
        assertTrue(notificationResponse.isSuccess());
        assertEquals("SMS",notificationResponse.getNotificationType());
        assertEquals("+918105270476",notificationResponse.getRecipient());
        assertEquals("SMS notification sent successfully to +918105270476",notificationResponse.getMessage());
        assertNotNull(notificationResponse.getTimestamp());

    }


}
