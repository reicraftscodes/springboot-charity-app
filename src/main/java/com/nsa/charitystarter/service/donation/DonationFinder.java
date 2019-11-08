package com.nsa.charitystarter.service.donation;

import com.nsa.charitystarter.domain.DonationSummary;
import com.nsa.charitystarter.domain.SponsorDonationInfo;

import java.util.List;
public interface DonationFinder {
    List<DonationSummary> findLastDonationsBySponsor(Integer sponsorId);

    SponsorDonationInfo getSponsorTotalDonationsInfo(Integer sponsorId);
}
