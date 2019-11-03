//package com.nsa.charitystarter.api;
//
//
//import com.nsa.charitystarter.controllers.SampleController;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//// web test for SampleAPI test (class)
//@RunWith(SpringRunner.class)
//@WebMvcTest(SampleController.class)
//public class SampleApiTest {
//
////    //injects MockMVC
////    @Autowired
////    private MockMvc mvc;
////
////    @Test
////    public void checkSampleApiResponseIsHello() throws Exception {
////        // performs get
////        mvc.perform(get("/sample"))
////                // expects HTTP 200
////                .andExpect(status().isOk())
////                // expects home view
////                .andExpect(view().name("sample"))
////                .andExpect(content()
////                        .string(containsString("Welcome to...."))
////                );
////
////    }
//}
