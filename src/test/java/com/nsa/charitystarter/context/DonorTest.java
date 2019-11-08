package com.nsa.charitystarter.context;

import com.nsa.charitystarter.domain.Charity;
import com.nsa.charitystarter.service.charity.CharityFinder;
import com.nsa.charitystarter.service.donation.DonationCreator;
import com.nsa.charitystarter.web.DonorForm;
import com.nsa.charitystarter.web.PaymentForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(com.nsa.charitystarter.web.DonorController.class)
public class DonorTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CharityFinder finder;

    @MockBean
    private DonationCreator donationCreator;


    @Test
    public void bhfDonationConfirmation() throws Exception {
        DonorForm donorForm = new DonorForm("aName", "ad1", "ad2", "aCity",
                "aPostcode", "aCountryISO", "aCountryName", 2.0d,
                Boolean.FALSE);


        Charity bhf = new Charity(
                4L,
                "British Heart Foundation",
                "12334444",
                "BHF",
                "heart, cardiac");

        PaymentForm paymentForm = new PaymentForm();

        HashMap<String, Object> sessionAttribute = new HashMap();
        sessionAttribute.put("donorKey", donorForm);
        sessionAttribute.put("charityKey", bhf);
        sessionAttribute.put("sponsorKey", 0);
        sessionAttribute.put("paymentKey", paymentForm);

        mvc.perform
                (
                        get
                                ("/confirm").sessionAttrs(sessionAttribute)
                )
                .andDo(
                        print()
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        content().string(
                                containsString("aName")
                        )
                )
                .andExpect(
                        content().string(
                                containsString(
                                        "British Heart Foundation"
                                )
                        )
                );
    }
}
