package com.nsa.charitystarter.web;

import com.nsa.charitystarter.domain.Charity;
import com.nsa.charitystarter.service.donation.DonationFinder;
import com.nsa.charitystarter.service.events.SponsorPageCreated;
import com.nsa.charitystarter.service.charity.CharityFinder;
import com.nsa.charitystarter.service.sponsorship.SponsorshipCreator;
import com.nsa.charitystarter.service.sponsorship.SponsorshipFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@SessionAttributes({"charityKey", "sponsorKey", "last5DonationsKey", "donationsTotalsKey"})
public class SponsorController {
    private CharityFinder charityFinder;
    private SponsorshipFinder sponsorFinder;
    private SponsorshipCreator sponsorshipCreator;
    private DonationFinder donationFinder;

    public SponsorController(CharityFinder aCharityFinder, SponsorshipFinder aSponsorFinder, SponsorshipCreator aCreator, DonationFinder aDonationFinder) {
        charityFinder = aCharityFinder;
        sponsorFinder = aSponsorFinder;
        sponsorshipCreator = aCreator;
        donationFinder = aDonationFinder;
    }

    static final Logger LOG = LoggerFactory.getLogger(SponsorController.class);


    @RequestMapping(path = "/sponsorship/{furl}", method = RequestMethod.GET)
    public String getSponsorshipPage(@PathVariable("furl") String furl, Model model) {

        Optional<SponsorPageCreated> sponsorship = sponsorFinder.findSponsorByFURL(furl);

        if (sponsorship.isPresent()) {
            model.addAttribute("sponsorshipKey", sponsorship.get());
            model.addAttribute("last5DonationsKey", donationFinder.findLastDonationsBySponsor(sponsorship.get().getId()));
            model.addAttribute("donationsTotalsKey", donationFinder.getSponsorTotalDonationsInfo(sponsorship.get().getId()));
            return "sponsor_page";
        } else {
            return "404";
        }

    }

    @RequestMapping(path = "/charity/{id}/createSponsorForm", method = RequestMethod.GET)
    public String startDonation(@PathVariable("id") Integer charityId, Model model) {
        model.addAttribute("sponsorKey", new SponsorForm());
        model.addAttribute("charityKey", charityFinder.findCharityByIndex(charityId).get());
        return "sponsor_form";
    }

    @RequestMapping(path = "sponsorDetails", method = RequestMethod.POST)
    public String sponsorDetails(@SessionAttribute("charityKey") Charity charityToBenefit,
                                 @ModelAttribute("sponsorKey") @Valid SponsorForm sponsor,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            LOG.error(bindingResult.toString());
            LOG.error("SponsorPageCreated Form has binding errors");
            return "sponsor_form";
        }
        LOG.debug("Sponsorship = " + sponsor);

        Optional<SponsorPageCreated> sponsorship = sponsorFinder.findSponsorByFURL(sponsor.getFurl());

        if(sponsorship.isEmpty()){
            SponsorPageCreated sponsorshipPageCreated = new SponsorPageCreated(
                    sponsor,
                    String.valueOf((int) (long) charityToBenefit.getId()));
            sponsorshipCreator.makeSponsorshipPage(sponsorshipPageCreated);
        } else {
            FieldError sponsorExistError = new FieldError("furl", "furl", "Furl already taken!");
            bindingResult.addError(sponsorExistError);
            return "sponsor_form";
        }

        return "sponsor_creation_receipt_page";
    }

}