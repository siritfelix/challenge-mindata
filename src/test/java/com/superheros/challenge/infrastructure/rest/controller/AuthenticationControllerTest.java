package com.superheros.challenge.infrastructure.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.jayway.jsonpath.JsonPath;
import com.superheros.challenge.TestUtil;
import com.superheros.challenge.shared.config.MenssageResponse;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void signupOK() throws Exception {
        mockMvc.perform(post(AuthenticationController.PATH.concat(AuthenticationController.SINGUP))
                .content(TestUtil.asJsonString(TestUtil.buidlSignUpRequest(TestUtil.EMAIL_11)))
                .contentType("application/json"))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void signupConflictException() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post(AuthenticationController.PATH.concat(AuthenticationController.SINGUP))
                        .content(TestUtil.asJsonString(TestUtil.buidlSignUpRequest(TestUtil.EMAIL_TOKEN)))
                        .contentType("application/json"))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        String code = JsonPath.parse(response).read("$.code");
        assertEquals(MenssageResponse.E409, code);
    }

    @Test
    public void signinOK() throws Exception {
        mockMvc.perform(post(AuthenticationController.PATH.concat(AuthenticationController.SINGIN))
                .content(TestUtil.asJsonString(TestUtil.buidlSignInRequest()))
                .contentType("application/json"))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void signinForbidden() throws Exception {
        mockMvc.perform(post(AuthenticationController.PATH.concat(AuthenticationController.SINGIN))
                .content(TestUtil.asJsonString(TestUtil.buidlSignUpRequest(TestUtil.EMAIL_2)))
                .contentType("application/json"))
                .andExpect(status().isForbidden()).andDo(MockMvcResultHandlers.print()).andReturn();
    }
}
