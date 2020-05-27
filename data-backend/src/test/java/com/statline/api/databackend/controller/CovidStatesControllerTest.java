package com.statline.api.databackend.controller;

import com.statline.api.databackend.utils.VerifyAPIKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CovidStatesControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql({"/schema.sql", "/data.sql"})
    void testIncorrectAPIKey() throws Exception {
        mockMvc.perform(get("/covid/states/latest").
                header("api_key", "nonsense")).andExpect(status().is4xxClientError());
    }

    @Test
    @Sql({"/schema.sql", "/data.sql"})
    void testLatest() throws Exception{
        String key = VerifyAPIKey.getGoogleKeys().iterator().next();
        MvcResult result = mockMvc.perform(get("/covid/states/latest").
                header("api_key",key)).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Sql({"/schema.sql", "/data.sql"})
    void testTotalNewCases() throws Exception{
        String key = VerifyAPIKey.getGoogleKeys().iterator().next();
        MvcResult result = mockMvc.perform(get("/covid/states/totalNewCases").
                header("api_key",key)).andExpect(status().isOk()).andReturn();
        assert (new Long(20).equals(Long.parseLong(result.getResponse().getContentAsString())));
    }
}