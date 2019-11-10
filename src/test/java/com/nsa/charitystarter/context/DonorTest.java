package com.nsa.charitystarter.context;

import com.nsa.charitystarter.domain.Charity;
import com.nsa.charitystarter.service.charity.CharityFinder;
import com.nsa.charitystarter.service.donation.DonationCreator;
import com.nsa.charitystarter.service.events.SponsorPageCreated;
import com.nsa.charitystarter.service.sponsorship.SponsorshipFinder;
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
import static org.mockito.ArgumentMatchers.anyInt;
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
    private SponsorshipFinder sponsorshipFinder;
    @MockBean
    private DonationCreator donationCreator;


    @Test
    public void donatingToNSPCCThroughForm() throws Exception {
        Charity nspcc = new Charity(
                1L,
                "National Society for Preventation of Cruelty to Children",
                "12345678",
                "NSPCC",
                "children",
                "Nspcc1.jpg");

        given(this.finder.findCharityByIndex(1)).willReturn(Optional.of(nspcc));
        given(this.sponsorshipFinder.findSponsorByID(anyInt())).willReturn(Optional.empty());

        mvc.perform
                (
                        get
                                ("/donateToCharity/1/0")
                )
                .andDo(
                        print()
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        content().string(
                                containsString("National Society for Preventation of Cruelty to Children")
                        )
                ).andExpect(
                content().string(containsString("<form action=\"/donorDetails\" method=\"post\">"))
        );
    }

    @Test
    public void donationConfirmationDisplayedForNSPCC() throws Exception {
        DonorForm donorForm = new DonorForm("aName", "ad1", "ad2", "aCity",
                "aPostcode", "aCountryISO", "aCountryName", 2.0d,
                Boolean.FALSE);


        Charity nspcc = new Charity(
                1L,
                "National Society for Preventation of Cruelty to Children",
                "12345678",
                "NSPCC",
                "children",
                "Nspcc1.jpg");

        PaymentForm paymentForm = new PaymentForm();

        HashMap<String, Object> sessionAttribute = new HashMap();
        sessionAttribute.put("donorKey", donorForm);
        sessionAttribute.put("charityKey", nspcc);
        sessionAttribute.put("sponsorKey", 0);
        sessionAttribute.put("paymentKey", paymentForm);
        sessionAttribute.put("sponsor", new SponsorPageCreated());

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
                                        "National Society for Preventation of Cruelty to Children"
                                )
                        )
                );
    }
}
