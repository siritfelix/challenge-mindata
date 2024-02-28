package com.superheros.challenge.infrastructure.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
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
public class SuperHeroControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private String token;

    @BeforeEach
    public void getToken() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post(AuthenticationController.PATH.concat(AuthenticationController.SINGIN))
                        .content(TestUtil.asJsonString(TestUtil.buidlSignInRequest()))
                        .contentType("application/json"))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        this.token = JsonPath.parse(response).read("$.token");
    }

    @Test
    public void forbidden() throws Exception {
        mockMvc.perform(get(SuperHeroController.PATH.concat(SuperHeroController.ALL))
                .param("id", TestUtil.ID_1.toString())
                .contentType("application/json"))
                .andExpect(status().isForbidden()).andDo(MockMvcResultHandlers.print()).andReturn();
        mockMvc.perform(get(SuperHeroController.PATH.concat(SuperHeroController.ALL))
                .param("id", TestUtil.ID_1.toString())
                .contentType("application/json"))
                .andExpect(status().isForbidden()).andDo(MockMvcResultHandlers.print());
        mockMvc.perform(post(SuperHeroController.PATH)
                .content(TestUtil.asJsonString(TestUtil.buildSuperHeroDto()))
                .contentType("application/json"))
                .andExpect(status().isForbidden()).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void findAllOk() throws Exception {
        mockMvc.perform(get(SuperHeroController.PATH.concat(SuperHeroController.ALL))
                .header("Authorization", "Bearer ".concat(token))
                .contentType("application/json"))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    public void findByIdOK() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(SuperHeroController.PATH).param("id", TestUtil.ID_1.toString())
                .header("Authorization", "Bearer ".concat(token))
                .contentType("application/json"))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");
        assertEquals(TestUtil.ID_1, id);
    }

    @Test
    public void findByIdBadRequestException() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(SuperHeroController.PATH)
                .header("Authorization", "Bearer ".concat(token))
                .contentType("application/json"))
                .andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        String code = JsonPath.parse(response).read("$.code");
        assertEquals(MenssageResponse.BR400, code);
    }

    @Test
    public void createOK() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(SuperHeroController.PATH)
                .header("Authorization", "Bearer ".concat(token))
                .content(TestUtil.asJsonString(TestUtil.buildSuperHeroDto()))
                .contentType("application/json"))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        String email = JsonPath.parse(response).read("$.name");
        assertEquals(TestUtil.EMAIL_1, email);
    }

    @Test
    public void createInternalError() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(SuperHeroController.PATH)
                .header("Authorization", "Bearer ".concat(token))
                .contentType("application/json"))
                .andExpect(status().isInternalServerError()).andDo(MockMvcResultHandlers.print()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        String code = JsonPath.parse(response).read("$.code");
        assertEquals(MenssageResponse.E500, code);
    }

    @Test
    public void upDateOk() throws Exception {
        MvcResult mvcResult = mockMvc.perform(patch(SuperHeroController.PATH)
                .header("Authorization", "Bearer ".concat(token))
                .content(TestUtil.asJsonString(TestUtil.buildSuperHeroDto(TestUtil.ID_1)))
                .contentType("application/json"))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        String email = JsonPath.parse(response).read("$.name");
        assertEquals(TestUtil.EMAIL_2, email);
    }

    @Test
    public void upDateBadRequestException() throws Exception {
        MvcResult mvcResult = mockMvc.perform(patch(SuperHeroController.PATH)
                .header("Authorization", "Bearer ".concat(token))
                .content(TestUtil.asJsonString(TestUtil.buildSuperHeroDto()))
                .contentType("application/json"))
                .andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        String code = JsonPath.parse(response).read("$.code");
        assertEquals(MenssageResponse.BR400, code);
    }

    @Test
    public void upDateNotFoundException() throws Exception {
        MvcResult mvcResult = mockMvc.perform(patch(SuperHeroController.PATH)
                .header("Authorization", "Bearer ".concat(token))
                .content(TestUtil.asJsonString(TestUtil.buildSuperHeroDto(TestUtil.ID_11)))
                .contentType("application/json"))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        String code = JsonPath.parse(response).read("$.code");
        assertEquals(MenssageResponse.F401, code);
    }

    @Test
    public void removeByIdOk() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(SuperHeroController.PATH).param("id", TestUtil.ID_3.toString())
                .header("Authorization", "Bearer ".concat(token))
                .contentType("application/json"))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        String code = JsonPath.parse(response).read("$.code");
        assertEquals(MenssageResponse.OK, code);
    }

    @Test
    public void removeByIdBadRequestException() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(SuperHeroController.PATH)
                .header("Authorization", "Bearer ".concat(token))
                .contentType("application/json"))
                .andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        String code = JsonPath.parse(response).read("$.code");
        assertEquals(MenssageResponse.BR400, code);
    }

    @Test
    public void removeByIdNotFoundException() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(SuperHeroController.PATH).param("id", TestUtil.ID_11.toString())
                .header("Authorization", "Bearer ".concat(token))
                .contentType("application/json"))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        String code = JsonPath.parse(response).read("$.code");
        assertEquals(MenssageResponse.F401, code);
    }

    @Test
    public void findByAllByFilterOk() throws Exception {
        mockMvc.perform(get(SuperHeroController.PATH.concat(SuperHeroController.FILTER))
                .param("name", "Super")
                .header("Authorization", "Bearer ".concat(token))
                .contentType("application/json"))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
    }

}
