package com.nsa.charitystarter.controllers;

import com.nsa.charitystarter.domain.models.Charity;
import com.nsa.charitystarter.service.CharitySearch;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CharityProfileController {

    private CharitySearch charitySearch;

    public CharityProfileController() {
        charitySearch = new CharitySearch();
    }

    @GetMapping("charity/{i}")
    public String showCharityPage(@PathVariable("i") Integer index, Model model) {

        Optional<Charity> charity = charitySearch.findCharityByIndex(index);

        if (charity.isPresent()) {
            model.addAttribute("charity", charity.get());
            return "charity";

        } else {
            return "404";
        }
    }

    @GetMapping("findCharity")
    public String findCharity(@RequestParam("search") String searchTerm, Model model) {

        List<Charity> charities = charitySearch.findCharityBySearch(searchTerm);

        model.addAttribute("searchTermKey", searchTerm);
        model.addAttribute("charityKey", charities);
        return "charityList";

    }



}

