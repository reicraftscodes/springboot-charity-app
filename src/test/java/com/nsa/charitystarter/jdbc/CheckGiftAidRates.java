package com.nsa.charitystarter.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@JdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CheckGiftAidRates {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void currentRateis25percent() throws Exception {

        Double currentRate = jdbcTemplate.queryForObject("select rate_percentage from gift_aid_rate where end_date is null", Double.class);
        assertEquals(Double.valueOf(25.0), currentRate);
    }
}
