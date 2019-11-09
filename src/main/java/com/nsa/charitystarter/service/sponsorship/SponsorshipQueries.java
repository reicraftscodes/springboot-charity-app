package com.nsa.charitystarter.service.sponsorship;

import com.nsa.charitystarter.domain.SponsorDonationInfo;
import com.nsa.charitystarter.service.events.SponsorPageCreated;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SponsorshipQueries implements SponsorshipFinder {

    private SponsorshipRepository sponsorRepository;

    public SponsorshipQueries(SponsorshipRepository repo) {
        sponsorRepository = repo;
    }

    public List<SponsorPageCreated> findSponsorByName(String title) {
        return
                sponsorRepository.findByName(title);
    }


    @Override
    public Optional<SponsorPageCreated> findSponsorByFURL(String furl) {
        return sponsorRepository.findByFURL(furl);
    }

    @Override
    public List<SponsorDonationInfo> getTop5Sponsorships(Long charityID) {
        return sponsorRepository.getTop5SponsorshipsForACharity(charityID);
    }

    @Override
    public List<SponsorDonationInfo> getRecentSponsorships(Long charityID) {
        return sponsorRepository.getRecentSponsorshipsForACharity(charityID);
    }

    @Override
    public Optional<SponsorPageCreated> findSponsorByID(Integer sponsorId) {
        return sponsorRepository.findByID(sponsorId);
    }
}