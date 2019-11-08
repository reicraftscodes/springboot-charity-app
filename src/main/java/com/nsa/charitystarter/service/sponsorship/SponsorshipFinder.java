package com.nsa.charitystarter.service.sponsorship;

import com.nsa.charitystarter.domain.SponsorDonationInfo;
import com.nsa.charitystarter.service.events.SponsorPageCreated;

import java.util.List;
import java.util.Optional;

public interface SponsorshipFinder {
    List<SponsorPageCreated> findSponsorByName(String title);

    Optional<SponsorPageCreated> findSponsorByFURL(String furl);

    List<SponsorDonationInfo> getTop5Sponsorships(Long charityID);

    List<SponsorDonationInfo> getRecentSponsorships(Long charityID);

    Optional<SponsorPageCreated> findSponsorByID(Integer sponsorId);
}
