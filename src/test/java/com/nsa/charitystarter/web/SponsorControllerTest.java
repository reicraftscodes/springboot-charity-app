package com.nsa.charitystarter.web;

import com.nsa.charitystarter.domain.Charity;
import com.nsa.charitystarter.domain.DonationSummary;
import com.nsa.charitystarter.domain.SponsorDonationInfo;
import com.nsa.charitystarter.service.charity.CharityFinder;
import com.nsa.charitystarter.service.donation.DonationFinder;
import com.nsa.charitystarter.service.events.SponsorPageCreated;
import com.nsa.charitystarter.service.sponsorship.SponsorshipCreator;
import com.nsa.charitystarter.service.sponsorship.SponsorshipFinder;
import com.nsa.charitystarter.web.controllers.SponsorController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class SponsorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private CharityFinder aCharityFinder;

    @Mock
    private SponsorshipFinder aSponsorFinder;

    @Mock
    private SponsorshipCreator aCreator;

    @Mock
    private DonationFinder aDonationFinder;

    @Mock
    private SponsorPageCreated sponsorPageCreated;

    @Mock
    private Charity charity;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(new SponsorController(aCharityFinder, aSponsorFinder, aCreator, aDonationFinder)).build();
    }

    @Test
    public void getSponsorshipPage_return_sponsor_page_when_sponsorPageCreated_is_present() throws Exception {
        String furl = "test";
        when(aSponsorFinder.findSponsorByFURL(furl)).thenReturn(Optional.of(sponsorPageCreated));
        when(sponsorPageCreated.getId()).thenReturn(123);

        DonationSummary donationSummary = mock(DonationSummary.class);
        List<DonationSummary> donationSummaries = Arrays.asList(donationSummary);

        when(aDonationFinder.findLastDonationsBySponsor(sponsorPageCreated.getId())).thenReturn(donationSummaries);

        SponsorDonationInfo sponsorDonationInfo = mock(SponsorDonationInfo.class);
        when(aDonationFinder.getSponsorTotalDonationsInfo(sponsorPageCreated.getId())).thenReturn(sponsorDonationInfo);

        mvc.perform(MockMvcRequestBuilders
                .get("/sponsorship/{furl}", furl)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("sponsor_page"))
                .andExpect(model().attribute("sponsorshipKey", sponsorPageCreated))
                .andExpect(model().attribute("last5DonationsKey", donationSummaries))
                .andExpect(model().attribute("donationsTotalsKey", sponsorDonationInfo));
    }

    @Test
    public void getSponsorshipPage_return_404_when_sponsorPageCreated_is_not_present() throws Exception {
        String furl = "test";
        when(aSponsorFinder.findSponsorByFURL(furl)).thenReturn(Optional.empty());
        mvc.perform(MockMvcRequestBuilders
                .get("/sponsorship/{furl}", furl)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("404"));
    }

    @Test
    public void startDonation_return_sponsor_form() throws Exception {
        Integer charityId = 123;
        when(aCharityFinder.findCharityByIndex(charityId)).thenReturn(Optional.of(charity));

        mvc.perform(MockMvcRequestBuilders
                .get("/charity/{id}/createSponsorForm", charityId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("sponsor_form"))
                .andExpect(model().attribute("sponsorKey", instanceOf(SponsorForm.class)))
                .andExpect(model().attribute("charityKey", charity));
    }

    @Test
    public void sponsorDetails_return_sponsor_form_when_sponsorFormHasErrors_FundraiserName_blank() throws Exception {
        SponsorForm sponsorForm = new SponsorForm();
        sponsorForm.setFundraiserName("");
        sponsorForm.setFundraisingAction("FundraisingAction");
        sponsorForm.setFurl("Furl");

        mvc.perform(MockMvcRequestBuilders
                .post("/sponsorDetails")
                .sessionAttr("charityKey", charity)
                .flashAttr("sponsorKey", sponsorForm))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("sponsor_form"));
    }

    @Test
    public void sponsorDetails_return_sponsor_form_when_sponsorFormISValid() throws Exception {
        SponsorForm sponsorForm = new SponsorForm();
        sponsorForm.setFundraiserName("FundraiserName");
        sponsorForm.setFundraisingAction("FundraisingAction");
        sponsorForm.setFurl("Furl");

        mvc.perform(MockMvcRequestBuilders
                .post("/sponsorDetails")
                .sessionAttr("charityKey", charity)
                .flashAttr("sponsorKey", sponsorForm))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("sponsor_creation_receipt_page"));
    }
}
