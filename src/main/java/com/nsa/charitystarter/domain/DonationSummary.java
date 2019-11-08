package com.nsa.charitystarter.domain;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DonationSummary {
    private String firstName;
    private String lastName;
    private Double amount;
    private LocalDateTime dateOfDonation;
}
