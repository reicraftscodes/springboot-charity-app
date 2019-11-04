package com.nsa.charitystarter.controllers;
import com.nsa.charitystarter.service.SponsorSearch;
import com.nsa.charitystarter.web.SponsorForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class SponsorProfileController {

    private SponsorSearch sponsorSearch;

    public SponsorProfileController(){
        sponsorSearch = new SponsorSearch();
    }

    @GetMapping("sponsor/{i}")
    public String showSponsorPage(@PathVariable("i") Integer index, Model model){

        Optional<SponsorForm> sponsor = sponsorSearch.findSponsorByIndex(index);

        if (sponsor.isPresent()) {
            model.addAttribute("sponsor", sponsor.get());
            return "sponsor";
        }else {
            return "404";
        }

    }

}
