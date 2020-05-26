package com.statline.api.databackend;

import com.statline.api.databackend.utils.VerifyAPIKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class APIKeyTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void testAPIKey() throws Exception {
        String key = VerifyAPIKey.getGoogleKeys().iterator().next();
        mockMvc.perform(get("/covid/states/latest").
                header("api_key",key)).andDo(print()).andExpect(status().isOk());
    }

}