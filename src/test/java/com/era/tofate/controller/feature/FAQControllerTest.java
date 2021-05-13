package com.era.tofate.controller.feature;

import com.era.tofate.ToFateApplication;
import com.era.tofate.entities.faq.FAQ;
import com.era.tofate.repository.userrole.UserRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = ToFateApplication.class)
@RunWith(SpringRunner.class)
public class FAQControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public void setup() {
         mvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Value("${app.bearer_token}")
    String token;

    @Test
    public void createFAQ() throws Exception {

        mvc.perform(post("/api/admin/createfaq")
                .contentType("application/json")
                .header("Authorization", "Bearer "+ token));
    }

    @SneakyThrows
    @Test
    public void getone() {

        mvc.perform( MockMvcRequestBuilders.get("/api/faq")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print());
    }

    @SneakyThrows
    @Test
    public void getAll() {

        mvc.perform( MockMvcRequestBuilders.get("/api/faq")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].answer").value("Что бы расположить к себе девушку, старайся удивлять ее и делать подарки "))
                .andExpect(jsonPath("$.[0].question").value("Как стать ближе к девушке"));
    }

    @SneakyThrows
    @Test
    public void editFAQ() {

        MockHttpServletRequestBuilder builder =
                put("/api/faq/editfaq/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .characterEncoding("UTF-8");
        this.mvc.perform(builder)
                .andDo(print());
    }

    @SneakyThrows
    @Test
    public void deleteFAQ() {

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete("/api/faq/deletefaq/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .characterEncoding("UTF-8");
        this.mvc.perform(builder)
                .andDo(MockMvcResultHandlers.print());
    }
}

