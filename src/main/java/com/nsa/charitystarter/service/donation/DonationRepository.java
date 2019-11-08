package com.nsa.charitystarter.service.donation;


import com.nsa.charitystarter.domain.DonationSummary;
import com.nsa.charitystarter.service.events.DonationMade;

import java.util.List;

public interface DonationRepository {
    void saveDonationEvent(DonationMade donationEvent);

    List<DonationSummary> getLastDonationsBySponsor(Integer sponsorId, Integer numberOfDonations);

    Double getTotalDonationAmount(Integer sponsorId);

    Double getTotalGiftAidDonationAmount(Integer sponsorId);

}
