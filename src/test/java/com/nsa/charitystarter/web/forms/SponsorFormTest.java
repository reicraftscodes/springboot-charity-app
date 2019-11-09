package com.nsa.charitystarter.web.forms;

import com.nsa.charitystarter.web.SponsorForm;
import org.junit.Test;

import static org.junit.Assert.*;

public class SponsorFormTest {
    private SponsorForm sponsorForm = new SponsorForm("fundraisingName", "action", "furl");

    @Test
    public void getFundraiserName() {
        assertEquals("fundraisingName", sponsorForm.getFundraiserName());

    }

    @Test
    public void getFundraisingAction() {
        assertEquals("action", sponsorForm.getFundraisingAction());
    }

    @Test
    public void getFurl() {
        assertEquals("furl", sponsorForm.getFurl());
    }

    @Test
    public void setFundraiserName() {
        sponsorForm.setFundraiserName("newFundName");
        assertEquals("newFundName", sponsorForm.getFundraiserName());
    }

    @Test
    public void setFundraisingAction() {
        sponsorForm.setFundraisingAction("newAct");
        assertEquals("newAct", sponsorForm.getFundraisingAction());
    }

    @Test
    public void setFurl() {
        sponsorForm.setFurl("newFurl");
        assertEquals("newFurl", sponsorForm.getFurl());
    }

    @Test
    public void doesNotEquals() {
        SponsorForm newSponsor = new SponsorForm();
        assertFalse(sponsorForm.equals(newSponsor));
    }

    @Test
    public void isEqual() {
        SponsorForm newSponsor = new SponsorForm("fundraisingName", "action", "furl");
        assertTrue(sponsorForm.equals(newSponsor));
    }

    @Test
    public void hashCode1() {
        SponsorForm newSponsor = new SponsorForm("fundraisingName", "action", "furl");
        assertTrue(newSponsor.hashCode() == sponsorForm.hashCode());
    }
}