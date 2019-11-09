package com.nsa.charitystarter.web.forms;

import com.nsa.charitystarter.web.DonorForm;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DonorFormTest {
    private DonorForm donorForm = new DonorForm();
    private DonorForm donorForm2 = new DonorForm();

    @Test
    public void setName() {
        donorForm.setName("Carl");
        assertEquals("Carl", donorForm.getName());
    }

    @Test
    public void setAddressLine1() {
        donorForm.setAddressLine1("cAdd1");
        assertEquals("cAdd1", donorForm.getAddressLine1());
    }

    @Test
    public void setAddressLine2() {
        donorForm.setAddressLine2("cAdd2");
        assertEquals("cAdd2", donorForm.getAddressLine2());
    }

    @Test
    public void setCity() {
        donorForm.setCity("cCardiff");
        assertEquals("cCardiff", donorForm.getCity());
    }

    @Test
    public void setPostcode() {
        donorForm.setPostcode(" CF243AA");
        assertEquals(" CF243AA", donorForm.getPostcode());
    }

    @Test
    public void setCountryName() {
        donorForm.setCountryName("United Kingdom");
        assertEquals("United Kingdom", donorForm.getCountryName());
    }

    @Test
    public void setDonationAmount() {
        donorForm.setDonationAmount(2d);
        assertEquals((Double) 2d, donorForm.getDonationAmount());
    }

    @Test
    public void setIsGiftAidEligible() {
        donorForm.setIsGiftAidEligible(Boolean.TRUE);
        assertTrue(donorForm.getIsGiftAidEligible());
    }

    @Test
    public void equals1() {
        assertTrue(donorForm.equals(donorForm2));
    }

    @Test
    public void hashCode1() {

        assertTrue(donorForm.hashCode() == donorForm2.hashCode());
    }
}
