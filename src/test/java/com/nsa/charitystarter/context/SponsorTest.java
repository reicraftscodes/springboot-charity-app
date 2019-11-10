package com.nsa.charitystarter.context;

import com.nsa.charitystarter.domain.Charity;
import com.nsa.charitystarter.domain.DonationSummary;
import com.nsa.charitystarter.domain.SponsorDonationInfo;
import com.nsa.charitystarter.service.charity.CharityFinder;
import com.nsa.charitystarter.service.donation.DonationFinder;
import com.nsa.charitystarter.service.events.SponsorPageCreated;
import com.nsa.charitystarter.service.sponsorship.SponsorshipCreator;
import com.nsa.charitystarter.service.sponsorship.SponsorshipFinder;
import com.nsa.charitystarter.web.SponsorControllerTest;
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
@WebMvcTest(SponsorControllerTest.class)
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
    public void createsSponsorFormForCorrectCharity() throws Exception {
        Charity nspcc = new Charity(
                1L,
                "National Society for Preventation of Cruelty to Children",
                "12345678",
                "NSPCC",
                "children",
                "Nspcc1.jpg");

        given(this.charityFinder.findCharityByIndex(1)).willReturn(Optional.of(nspcc));
        HashMap<String, Object> sessAtr = new HashMap();
        sessAtr.put("sponsorKey", new SponsorForm());
        mvc.perform
                (
                        get
                                ("/charity/1/createSponsorForm").sessionAttrs(sessAtr)
                )
                .andDo(
                        print()
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        content().string(
                                containsString("NSPCC")
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

    @Test
    public void gotoCollySponsor() throws Exception {
        SponsorPageCreated ccohalane2Sponsor = new SponsorPageCreated(
                611, "Colly Cohalan", "8", "Etiam pretium iaculis justo. In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus. Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi. Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque.",
                LocalDateTime.of(2017, 8, 9, 0, 0),
                LocalDateTime.of(2016, 11, 14, 0, 0),
                LocalDateTime.of(2017, 4, 18, 0, 0),
                "ccohalane2");

        List<DonationSummary> listSum = List.of(new DonationSummary("fName", "lname", 12d,
                LocalDateTime.of(2017, 8, 9, 0, 0)));

        SponsorDonationInfo sponsorDonationStats = new SponsorDonationInfo("title", "ccohalane2", 12d, 2d);

        given(this.sponsorFinder.findSponsorByFURL("ccohalane2")).willReturn(Optional.of(ccohalane2Sponsor));
        given(this.donationFinder.findLastDonationsBySponsor(ccohalane2Sponsor.getId())).willReturn(listSum);
        given(this.donationFinder.getSponsorTotalDonationsInfo(ccohalane2Sponsor.getId())).willReturn(sponsorDonationStats);


        mvc.perform(
                get
                        ("/sponsorship/ccohalane2")
        )
                .andDo(
                        print()
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        content().string(
                                containsString("Colly Cohalan")
                        )
                );

    }


}
