package com.praneeth.notificationservice.exception;

import com.praneeth.notificationservice.controller.NotificationController;
import com.praneeth.notificationservice.dto.EmailNotificationRequest;
import com.praneeth.notificationservice.service.NotificationService;
import org.junit.jupiter.api.Test;
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
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private NotificationService notificationService;

    @Test
    void shouldReturn400_WhenValidationFails() throws Exception {
        String request = """
                {
                    "recipientEmail": "wrong-email",
                    "subject": "Test",
                    "messageBody": "Test message"
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
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturn500_WhenUnexpectedExceptionOccurs() throws Exception {
        EmailNotificationRequest request = new EmailNotificationRequest();
        request.setRecipientEmail("praneeth@gmail.com");
        request.setSubject("Test Subject");
        request.setMessageBody("Test Message");

        when(notificationService.sendEmail(any(EmailNotificationRequest.class)))
                .thenThrow(new RuntimeException("Something went wrong"));

        mockMvc.perform(post("/notifications/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/notifications/email"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
