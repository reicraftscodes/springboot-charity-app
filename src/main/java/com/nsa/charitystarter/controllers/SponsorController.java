package com.nsa.charitystarter.controllers;

import com.nsa.charitystarter.controllers.DonorController;
import com.nsa.charitystarter.web.SponsorForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class SponsorController {

    static final Logger LOG = LoggerFactory.getLogger(DonorController.class);

    @RequestMapping(path = "/makeSponsor/", method = RequestMethod.GET)
    public String makeSponsor(Model model) {

        model.addAttribute("sponsor", new SponsorForm());
        return "sponsorPage";

    }

    @RequestMapping(path = "sponsorDetails", method = RequestMethod.POST)
    public String sponsorDetails(Model model, @ModelAttribute("sponsor") @Valid SponsorForm sponsor, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            LOG.error(bindingResult.toString());
            LOG.error("Sponsor Form has binding errors");
            model.addAttribute("sponsor", sponsor);
            return "sponsorPage";
        }

        LOG.debug(sponsor.toString());

        model.addAttribute("sponsor", sponsor);

        return "donation";
    }


}
