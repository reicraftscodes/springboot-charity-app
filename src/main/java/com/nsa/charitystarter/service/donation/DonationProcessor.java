package com.nsa.charitystarter.service.donation;

import com.nsa.charitystarter.service.events.DonationMade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DonationProcessor implements DonationCreator {

    private DonationRepository donationRepository;

    public DonationProcessor(DonationRepository aRepo) {
        donationRepository = aRepo;
    }

    @Override
    public void makeDonation(DonationMade donation) {

        donationRepository.saveDonationEvent(donation);

    }

}