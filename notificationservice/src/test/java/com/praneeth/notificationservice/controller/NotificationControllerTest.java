package com.praneeth.notificationservice.controller;

import com.praneeth.notificationservice.dto.EmailNotificationRequest;
import com.praneeth.notificationservice.dto.NotificationResponse;
import com.praneeth.notificationservice.dto.SmsNotificationRequest;
import com.praneeth.notificationservice.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    Logger log = LoggerFactory.getLogger(NotificationControllerTest.class);
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    NotificationService notificationService;

    @Test
    void sendEmailTest() throws Exception {
        log.info("controller beginning test for email");
        //Arrange
        EmailNotificationRequest emailNotificationRequest = new EmailNotificationRequest();
        emailNotificationRequest.setSubject("Test Subject");
        emailNotificationRequest.setRecipientEmail("praneeth@gmail.com");
        emailNotificationRequest.setMessageBody("Test Controller");
        log.info("arrange step");
        // response
        NotificationResponse response = new NotificationResponse(
                true,
                "EMAIL notification sent successfully to praneeth@gmail.com",
                "EMAIL",
                "praneeth@gmail.com"
        );
        // Act & Assign
        when(notificationService.sendEmail(any(EmailNotificationRequest.class)))
                .thenReturn(response);
        mockMvc.perform(post("/notifications/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailNotificationRequest))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("EMAIL notification sent successfully to praneeth@gmail.com"))
                .andExpect(jsonPath("$.notificationType").value("EMAIL"))
                .andExpect(jsonPath("$.recipient")
                        .value("praneeth@gmail.com"));


        log.info("controller ending test");
    }

    @Test
    void sendSmsTest() throws Exception {
        log.info("controller beginning test for sms");
        //Arrange
        SmsNotificationRequest smsNotificationRequest = new SmsNotificationRequest();
        smsNotificationRequest.setMessage("This is a sms message");
        smsNotificationRequest.setRecipientPhone("+918105270476");
        log.info("sms arrange step");
        // Response
        NotificationResponse response = new NotificationResponse(
                true,
                "SMS notification sent successfully to +918105270476",
                "SMS",
                "+918105270476"
        );
        // Act & Assign
        when(notificationService.sendSms(any(SmsNotificationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/notifications/sms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(smsNotificationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("SMS notification sent successfully to +918105270476"))
                .andExpect(jsonPath("$.notificationType").value("SMS"))
                .andExpect(jsonPath("$.recipient").value("+918105270476"));
    }

    @Test
    void sendEmail_ShouldReturn400_WhenEmailIsInvalid() throws Exception {
        log.info("controller beginning test for Invalid Email");
        // we are following this approach for Creating malformed or invalid payload
        // because Creating malformed JSON with a DTO isn't possible
        // as serialization always produces valid JSON.
        String request = """
                {
                    "recipientEmail": "Hithere",
                    "subject": "Test Subject",
                    "messageBody": "Test Message"
                }
                """;
        mockMvc.perform(post("/notifications/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.message")
                        .value("One or more fields failed validation. See 'fieldErrors' for details."))
                .andExpect(jsonPath("$.path").value("/notifications/email"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("recipientEmail"))
                .andExpect(jsonPath("$.fieldErrors[0].rejectedValue").value("Hithere"))
                .andExpect(jsonPath("$.fieldErrors[0].reason")
                        .value("Recipient email must be a valid email address"))
                .andExpect(jsonPath("$.timestamp").exists());
        log.info("controller ending test");

    }

    @Test
    void sendSms_ShouldReturn400_WhenSmsIsInvalid() throws Exception {
        log.info("controller beginning test for invalid SMS");
        String request = """
            {
                "recipientPhone": "12345",
                "message": "Test Message"
            }
            """;
        mockMvc.perform(post("/notifications/sms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.message")
                        .value("One or more fields failed validation. See 'fieldErrors' for details."))
                .andExpect(jsonPath("$.path").value("/notifications/sms"))
                .andExpect(jsonPath("$.fieldErrors[0].field").value("recipientPhone"))
                .andExpect(jsonPath("$.fieldErrors[0].rejectedValue").value("12345"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void sendEmail_ShouldReturn400_WhenRequiredFieldAreBlank() throws Exception {
        log.info("controller beginning test for required field blank");
        String request = """
                {
                    "recipientEmail": "",
                    "subject": "",
                    "messageBody": "Test Message"
                }
                """;
        mockMvc.perform(post("/notifications/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.path").value("/notifications/email"))
                .andExpect(jsonPath("$.fieldErrors").isArray())
                .andExpect(jsonPath("$.timestamp").exists());
        log.info("controller ending test");
    }
    @Test
    void sendSms_ShouldReturn400_WhenRequiredFieldAreBlank() throws Exception{
        log.info("controller beginning test for invalid SMS");
        String request = """
            {
                "recipientPhone": "",
                "message": ""
            }
            """;
        mockMvc.perform(post("/notifications/sms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.path").value("/notifications/sms"))
                .andExpect(jsonPath("$.fieldErrors").isArray())
                .andExpect(jsonPath("$.timestamp").exists());
        log.info("controller ending test");}
}
