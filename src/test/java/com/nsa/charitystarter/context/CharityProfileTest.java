//package com.nsa.charitystarter.context;
//
//import com.nsa.charitystarter.domain.Charity;
//import com.nsa.charitystarter.service.charity.CharityFinder;
//import com.nsa.charitystarter.service.sponsorship.SponsorshipFinder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(CharityProfileTest.class)
//public class CharityProfileTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private CharityFinder charityFinder;
//
//    @MockBean
//    private SponsorshipFinder sponsorshipFinder;
//
//    @Test
//    public void charityIsBhf() throws Exception {
//
//        Charity bhf = new Charity(
//                4L,
//                "British Heart Foundation",
//                "12334444",
//                "BHF",
//                "heart, cardiac");
//
//        given(this.charityFinder.findCharityByIndex(6)).willReturn(Optional.of(bhf));
//
//        mvc.perform
//                (get
//                        ("/charity/6")
//                )
//                .andDo(
//                        print()
//                )
//                .andExpect(
//                        status().isOk()
//                )
//                .andExpect(
//                        content().string(containsString("British Heart"))
//                )
//
//        ;
//
//    }
//}
