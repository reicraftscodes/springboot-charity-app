package com.nsa.charitystarter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class SponsorDonationInfo {
    private String titleOfSponsorship;
    private String furl;
    private Double totalDonationsAmount;
    private Double totalGiftAidAmount;
}
