package com.nsa.charitystarter.service.sponsorship;

import com.nsa.charitystarter.service.events.SponsorPageCreated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SponsorshipProcessor implements SponsorshipCreator {

    private SponsorshipRepository sponsorshipRepository;

    public SponsorshipProcessor(SponsorshipRepository aRepo) {
        sponsorshipRepository = aRepo;
    }

    @Override
    public void makeSponsorshipPage(SponsorPageCreated sponsorshipPageCreated) {
        sponsorshipRepository.saveSponsorshipEvent(sponsorshipPageCreated);
    }
}
