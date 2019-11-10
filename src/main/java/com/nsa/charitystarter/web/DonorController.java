package com.nsa.charitystarter.web;

import com.nsa.charitystarter.domain.Charity;
import com.nsa.charitystarter.service.charity.CharityFinder;
import com.nsa.charitystarter.service.donation.DonationCreator;
import com.nsa.charitystarter.service.events.DonationMade;
import com.nsa.charitystarter.service.events.SponsorPageCreated;
import com.nsa.charitystarter.service.sponsorship.SponsorshipFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@SessionAttributes({"charityKey", "donorKey", "paymentKey", "sponsorKey", "sponsor"})
public class DonorController {
    private CharityFinder finder;
    private DonationCreator donationCreator;
    private SponsorshipFinder sponsorshipFinder;

    //@Autowired
    public DonorController(CharityFinder aFinder, DonationCreator aCreator, SponsorshipFinder aSponsorshipFinder) {
        finder = aFinder;
        donationCreator = aCreator;
        sponsorshipFinder = aSponsorshipFinder;
    }

    static final Logger LOG = LoggerFactory.getLogger(DonorController.class);


    @RequestMapping(path = "/donateToCharity/{id}/{sponsorId}", method = RequestMethod.GET)
    public String startDonation(@PathVariable("id") Integer charityId, @PathVariable("sponsorId") Integer sponsorId, Model model) {
        Optional<Charity> charityToBenefit = finder.findCharityByIndex(charityId);
        if (charityToBenefit.isPresent()) {
            Optional<SponsorPageCreated> sponsorPageCreated = sponsorshipFinder.findSponsorByID(sponsorId);
            model.addAttribute("sponsor", sponsorPageCreated.orElseGet(SponsorPageCreated::new));

            LOG.debug("Putting charity on  model (and therefore on session) with key 'charity'");
            model.addAttribute("charityKey", charityToBenefit.get());
            model.addAttribute("donorKey", new DonorForm());
            model.addAttribute("sponsorKey", sponsorId);

            return "donor_details_page";

        }
        return "404";
    }

        @RequestMapping(path = "donorDetails", method = RequestMethod.POST)
        public String donorDetails (@SessionAttribute("charityKey") Charity charityToBenefit,
                @ModelAttribute("donorKey") @Valid DonorForm donor, //from form
                @ModelAttribute("sponsorKey") Integer sponsorId,
                @ModelAttribute("sponsor") SponsorPageCreated sponsor,
                BindingResult bindingResult,
                Model model){

            if (bindingResult.hasErrors()) {
                LOG.error(bindingResult.toString());
                LOG.error("Donation Form has binding errors");
                return "donor_details_page";
            }

            LOG.debug(donor.toString());
            LOG.debug("Charity = " + charityToBenefit);

            model.addAttribute("paymentKey", new PaymentForm());


            return "donation_payment_page";
        }

        @RequestMapping(path = "paymentDetails", method = RequestMethod.POST)
        public String paymentDetails (@SessionAttribute("donorKey") DonorForm donor,
                @SessionAttribute("charityKey") Charity charityToBenefit,
                @ModelAttribute("sponsorKey") Integer sponsorId,
                @SessionAttribute("sponsor") SponsorPageCreated sponsor,
                @ModelAttribute("paymentKey") @Valid PaymentForm payment,
                BindingResult bindingResult,
                Model model){

            LOG.debug("From session..." + donor);
            LOG.debug("From session..." + charityToBenefit);

            if (bindingResult.hasErrors()) {
                LOG.error(bindingResult.toString());
                LOG.error("Payment Form has binding errors");
                return "donation_payment_page";
            }


            model.addAttribute("last4CardKey", payment.getCardNumber().substring(payment.getCardNumber().length() - 4));


            return "payment_confirmation_page";
        }

        @RequestMapping(path = "/confirm", method = RequestMethod.GET)
        public String confirmDonation (@SessionAttribute("donorKey") DonorForm donor,
                @SessionAttribute("charityKey") Charity charityToBenefit,
                @ModelAttribute("sponsorKey") Integer sponsorId,
                @ModelAttribute("sponsor") SponsorPageCreated sponsor,
                @SessionAttribute("paymentKey") PaymentForm payment,
                Model model,
                HttpSession session){


            DonationMade donation = new DonationMade(
                    charityToBenefit,
                    sponsorId,
                    donor.getName(),
                    donor.getAddressLine1(),
                    donor.getAddressLine2(),
                    donor.getCity(),
                    donor.getPostcode(),
                    "GB",
                    donor.getDonationAmount(),
                    donor.getIsGiftAidEligible(),
                    LocalDateTime.now(),
                    "pay");

            donationCreator.makeDonation(donation);


            session.invalidate();
            return "donation_receipt_page";
        }

    }