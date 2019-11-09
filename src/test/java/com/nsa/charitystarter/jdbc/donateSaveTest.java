package com.nsa.charitystarter.jdbc;

import com.nsa.charitystarter.data.DonationRepoJDBC;
import com.nsa.charitystarter.domain.Charity;
import com.nsa.charitystarter.service.donation.DonationRepository;
import com.nsa.charitystarter.service.events.DonationMade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@JdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@ContextConfiguration(classes = {DonationRepoJDBC.class,})
public class donateSaveTest {
    @Autowired
    private DonationRepository repo;


    @Test
    public void savesDonationMade() throws Exception {
        Charity charity = new Charity(
                4L,
                "British Heart Foundation",
                "12334444",
                "BHF",
                "heart, cardiac");

        DonationMade donationMade = new DonationMade(charity, 0, "aName", "aAd1",
                "aAd2", "aCity", "aPostCode", "245", 1D,
                Boolean.FALSE, LocalDateTime.now(), "789");

        repo.saveDonationEvent(donationMade);

        assertTrue(((Double) 1D).equals(repo.getTotalDonationAmount(0)));


    }
}
