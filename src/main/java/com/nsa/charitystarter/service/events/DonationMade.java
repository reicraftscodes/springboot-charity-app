package com.nsa.charitystarter.service.events;

import com.nsa.charitystarter.domain.Charity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DonationMade {
    private Charity theCharity;
    private Integer sponsor_form_id = null;
    private String donorName;
    private String donorAddress1;
    private String donorAddress2;
    private String donorCity;
    private String donorPostcode;
    private String donorCountry = "GB";
    private Double donationAmount;
    private Boolean isGiftAidEligible;
    private LocalDateTime donationTime;
    private String paymentReference = "payment";
}
