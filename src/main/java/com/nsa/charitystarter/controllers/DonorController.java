package com.nsa.charitystarter.controllers;

import com.nsa.charitystarter.domain.models.Charity;
import com.nsa.charitystarter.service.CharitySearch;
import com.nsa.charitystarter.web.DonorForm;
import com.nsa.charitystarter.web.PaymentForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@SessionAttributes({"charity", "donor", "payment"})
public class DonorController {

    private CharitySearch charitySearch;

    public DonorController() {
        charitySearch = new CharitySearch();
    }

    static final Logger LOG = LoggerFactory.getLogger(DonorController.class);

    @RequestMapping(path = "/donateToCharity/{id}", method = RequestMethod.GET)
    public String startDonation(@PathVariable("id") Integer charityId, Model model) {

        Charity charityToBenefit = charitySearch.findCharityByIndex(charityId).get();
        LOG.debug("Putting charity on  model (and therefore on session) with key 'charity'");
        model.addAttribute("charity", charityToBenefit);
        model.addAttribute("donor", new DonorForm());
        return "donation";

    }

    @RequestMapping(path = "donorDetails", method = RequestMethod.POST)
    public String donorDetails(@SessionAttribute("charity") Charity charityToBenefit,
                               @ModelAttribute("donor") @Valid DonorForm donor, //from form
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            LOG.error(bindingResult.toString());
            LOG.error("Donation Form has binding errors");
            return "donation";
        }
        LOG.debug(donor.toString());
        LOG.debug("Charity = " + charityToBenefit);

        model.addAttribute("payment", new PaymentForm());

        return "donationPayment";
    }

    @RequestMapping(path = "paymentDetails", method = RequestMethod.POST)
    public String donorDetails(@SessionAttribute("donor") DonorForm donor,
                               @SessionAttribute("charity") Charity charityToBenefit,
                               @ModelAttribute("payment") @Valid PaymentForm payment,
                               BindingResult bindingResult,
                               Model model) {
        LOG.debug("From session..." + donor);
        LOG.debug("From session..." + charityToBenefit);

        if (bindingResult.hasErrors()) {
            LOG.error(bindingResult.toString());
            LOG.error("Payment Form has binding errors");
            return "donationPayment";
        }


        model.addAttribute("last4Card", payment.getCardNumber().substring(payment.getCardNumber().length() - 4));


        return "paymentConfirmation";
    }

    @RequestMapping(path = "/confirm", method = RequestMethod.GET)
    public String confirmDonation(@SessionAttribute("donor") DonorForm donor,
                                  @SessionAttribute("charity") Charity charityToBenefit,
                                  @SessionAttribute("payment") PaymentForm payment,
                                  Model model,
                                  HttpSession session) {


        //donation processing happen
        session.invalidate();
        return "receipt";
    }



}
