package com.nsa.charitystarter.context;

import com.nsa.charitystarter.domain.Charity;
import com.nsa.charitystarter.domain.DonationSummary;
import com.nsa.charitystarter.domain.SponsorDonationInfo;
import com.nsa.charitystarter.service.charity.CharityFinder;
import com.nsa.charitystarter.service.donation.DonationFinder;
import com.nsa.charitystarter.service.events.SponsorPageCreated;
import com.nsa.charitystarter.service.sponsorship.SponsorshipCreator;
import com.nsa.charitystarter.service.sponsorship.SponsorshipFinder;
import com.nsa.charitystarter.web.SponsorForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(com.nsa.charitystarter.web.SponsorController.class)
public class SponsorTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CharityFinder charityFinder;
    @MockBean
    private SponsorshipFinder sponsorFinder;
    @MockBean
    private SponsorshipCreator sponsorshipCreator;
    @MockBean
    private DonationFinder donationFinder;

    @Test
    public void sponsorCreate() throws Exception {
        Charity crukCharity = new Charity(
                3L,
                "Cancer Research UK",
                "22345678",
                "CRUK",
                "cancer, research, oncology");

        given(this.charityFinder.findCharityByIndex(2)).willReturn(Optional.of(crukCharity));
        HashMap<String, Object> sessionattr = new HashMap();
        sessionattr.put("sponsorKey", new SponsorForm());
        mvc.perform
                (
                        get
                                ("/charity/2/createSponsorForm").sessionAttrs(sessionattr)
                )
                .andDo(
                        print()
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        content().string(
                                containsString("CRUK")
                        )
                )
                .andExpect(
                        content().string(
                                containsString(
                                        "Name for Fundraising Page"
                                )
                        )
                );
    }


}
