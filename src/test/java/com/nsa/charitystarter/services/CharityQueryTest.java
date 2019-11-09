package com.nsa.charitystarter.services;

import com.nsa.charitystarter.data.CharityRepoJDBC;
import com.nsa.charitystarter.domain.Charity;
import com.nsa.charitystarter.service.charity.CharityFinder;
import com.nsa.charitystarter.service.charity.CharityQueries;
import com.nsa.charitystarter.service.charity.CharityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
@ContextConfiguration(classes = {CharityQueries.class, CharityRepoJDBC.class,})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CharityQueryTest {
    @Autowired
    private CharityRepository repo;
    @Autowired
    private CharityFinder finder;

    @Test
    public void findNSPCCByIndex1() {
        Optional<Charity> aCharity = finder.findCharityByIndex(1);
        assertEquals("NSPCC", aCharity.get().getAcronym());
    }

    @Test
    public void findKRUKCharityBySearch() {
        List<Charity> aCharity = finder.findCharityBySearch("heart");
        assertEquals("BHF", aCharity.get(0).getAcronym());
    }
}