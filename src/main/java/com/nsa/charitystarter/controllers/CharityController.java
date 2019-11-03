//package com.nsa.charitystarter.controllers;
//
//import com.nsa.charitystarter.domain.models.Charity;
//import com.nsa.charitystarter.repositories.CharityRepository;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//public class CharityController {
//
//    private CharityRepository repo;
//
//    public CharityController(CharityRepository repo) {
//        this.repo = repo;
//    }
//
//    @GetMapping("charity/{i}")
//    public String showCharityPage(@PathVariable("i") Integer index, Model model) {
//
//        Optional<Charity> charity = repo.findById(index.longValue());
//
//        if (charity.isPresent()) {
//            model.addAttribute("charityKey", charity.get());
//            return "charityProfile";
//
//        } else {
//            return "404";
//        }
//    }
//
//    @GetMapping("charities")
//    public String findCharity(@RequestParam("search") String searchTerm, Model model) {
//
//        List<Charity> charities = repo.findBySearch(searchTerm);
//
//        model.addAttribute("searchTermKey", searchTerm);
//        model.addAttribute("charitiesKey", charities);
//        return "charityList";
//
//    }
//}
