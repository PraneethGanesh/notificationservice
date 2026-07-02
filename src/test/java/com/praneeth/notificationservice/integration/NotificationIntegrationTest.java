package com.praneeth.notificationservice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void emailNotification_ShouldWork_EndToEnd() throws Exception {
        String request = """
                {
                    "recipientEmail": "praneeth@gmail.com",
                    "subject": "Integration Test",
                    "messageBody": "Testing full flow"
                }
                """;

        mockMvc.perform(post("/notifications/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.notificationType").value("EMAIL"))
                .andExpect(jsonPath("$.recipient").value("praneeth@gmail.com"));
    }

    @Test
    void emailNotification_ShouldReturn400_WhenEmailIsInvalid() throws Exception {
        String request = """
                {
                    "recipientEmail": "wrong-email",
                    "subject": "Integration Test",
                    "messageBody": "Testing validation"
                }
                """;

        mockMvc.perform(post("/notifications/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.path").value("/notifications/email"));
    }
}
