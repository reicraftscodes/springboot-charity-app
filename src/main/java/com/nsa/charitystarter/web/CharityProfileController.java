package com.nsa.charitystarter.web;

import com.nsa.charitystarter.domain.Charity;
import com.nsa.charitystarter.domain.SponsorDonationInfo;
import com.nsa.charitystarter.service.charity.CharityFinder;
import com.nsa.charitystarter.service.sponsorship.SponsorshipFinder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CharityProfileController {

    private CharityFinder charityFinder;
    private SponsorshipFinder sponsorshipFinder;

    public CharityProfileController(CharityFinder aCharityFinder, SponsorshipFinder aSponsorshipFinder) {
        charityFinder = aCharityFinder;
        sponsorshipFinder = aSponsorshipFinder;
    }

    @GetMapping("charity/{i}")
    public String showCharityPage(@PathVariable("i") Integer index, Model model) {

        Optional<Charity> charity = charityFinder.findCharityByIndex(index);

        List<SponsorDonationInfo> top5Sponsorships = sponsorshipFinder.getTop5Sponsorships(charity.get().getId());
        List<SponsorDonationInfo> recentSponsorships = sponsorshipFinder.getRecentSponsorships(charity.get().getId());

        if (charity.isPresent()) {
            model.addAttribute("charityKey", charity.get());
            model.addAttribute("top5SponsorshipsKey", top5Sponsorships);
            model.addAttribute("recentSponsorshipsKey", recentSponsorships);
            return "charity_profile_page";

        } else {
            return "404";
        }
    }

    @GetMapping("findCharity")
    public String findCharity(@RequestParam("search") String searchTerm, Model model) {

        List<Charity> charities = charityFinder.findCharityBySearch(searchTerm);

        model.addAttribute("searchTermKey", searchTerm);
        model.addAttribute("charitiesKey", charities);
        return "charity_list_page";

    }

}
