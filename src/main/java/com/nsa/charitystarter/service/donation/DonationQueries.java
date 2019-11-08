package com.nsa.charitystarter.service.donation;

import com.nsa.charitystarter.domain.DonationSummary;
import com.nsa.charitystarter.domain.SponsorDonationInfo;
import com.nsa.charitystarter.service.sponsorship.SponsorshipFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationQueries implements DonationFinder {

    private DonationRepository donationRepository;
    private SponsorshipFinder sponsorshipFinder;

    public DonationQueries(DonationRepository repo, SponsorshipFinder aSponsorshipFinder) {
        donationRepository = repo;
        sponsorshipFinder = aSponsorshipFinder;

    }

    @Override
    public List<DonationSummary> findLastDonationsBySponsor(Integer sponsorId) {
        return donationRepository.getLastDonationsBySponsor(sponsorId, 5);
    }

    @Override
    public SponsorDonationInfo getSponsorTotalDonationsInfo(Integer sponsorId) {
        String title = sponsorshipFinder.findSponsorByID(sponsorId).get().getFundraiserName();
        String furl = sponsorshipFinder.findSponsorByID(sponsorId).get().getFurl();
        Double totalDonations = donationRepository.getTotalDonationAmount(sponsorId);
        Double totalGiftAid = donationRepository.getTotalGiftAidDonationAmount(sponsorId);
        return new SponsorDonationInfo(title, furl, totalDonations, totalGiftAid);
    }
}
