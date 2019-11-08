package com.nsa.charitystarter.service.sponsorship;

import com.nsa.charitystarter.domain.SponsorDonationInfo;
import com.nsa.charitystarter.service.events.SponsorPageCreated;

import java.util.List;
import java.util.Optional;

public interface SponsorshipRepository {
    public List<SponsorPageCreated> findByName(String name);

    public Optional<SponsorPageCreated> findByFURL(String furl);

    public Optional<SponsorPageCreated> findByID(Integer ID);

    public void saveSponsorshipEvent(SponsorPageCreated sponsorshipPageCreated);

    public List<SponsorDonationInfo> getTop5SponsorshipsForACharity(Long charityID);

    public List<SponsorDonationInfo> getRecentSponsorshipsForACharity(Long charityID);
}
