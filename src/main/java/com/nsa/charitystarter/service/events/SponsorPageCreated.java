package com.nsa.charitystarter.service.events;

import com.nsa.charitystarter.web.SponsorForm;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
public class SponsorPageCreated {
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(1000);
    private Integer id = ID_GENERATOR.getAndIncrement();
    private String fundraiserName;
    private String charityID;
    private String fundraisingAction;
    private LocalDateTime dateCreated;
    private LocalDateTime firstValidDay;
    private LocalDateTime lastValidDay;
    private String furl;

    public SponsorPageCreated() {
    }

    public SponsorPageCreated(SponsorForm sponsorForm, String aCharityID) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.fundraiserName = sponsorForm.getFundraiserName();
        this.fundraisingAction = sponsorForm.getFundraisingAction();
        this.furl = sponsorForm.getFurl();
        this.charityID = aCharityID;
        this.dateCreated = LocalDateTime.now();
        this.firstValidDay = LocalDateTime.now();
        this.lastValidDay = LocalDateTime.now().plusYears(1L);

    }

}