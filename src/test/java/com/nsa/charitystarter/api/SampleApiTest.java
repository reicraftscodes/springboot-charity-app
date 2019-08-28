package com.nsa.charitystarter.api;


import com.nsa.charitystarter.controllers.SampleController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleApiTest {

        @Autowired
        private MockMvc mvc;

        @Test
        public void checkSampleApiResponseIsHello() throws Exception {

            mvc.perform(get("/sample"))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .string(containsString("This is a sample response"))
                    );

        }
}
