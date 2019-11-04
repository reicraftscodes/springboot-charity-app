package com.nsa.charitystarter.service;

import com.nsa.charitystarter.web.SponsorForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SponsorSearch {

    private List<SponsorForm> sponsors = new ArrayList<>();

    public SponsorSearch() {

        SponsorForm Carl = new SponsorForm(
                "Carl Jones",
                "Running for Cardiff 21K Marathon",
                "/sponsor/1");

        SponsorForm James = new SponsorForm(
                "James Osbourne",
                "Charity Ball Fundraising",
                "/sponsor/2");

        SponsorForm Natasha = new SponsorForm(
                "Natasha Edwards",
                "Oracle & MySQl Workshop",
                "/sponsor/3");

        sponsors = List.of(Carl, James, Natasha);
    }

    /**
     * Return the charity according to position in the list with index starting  at 1
     *
     * @param index
     * @return
     */
    public Optional<SponsorForm> findSponsorByIndex(Integer index) {
        if (index < 1 || index > sponsors.size()) {
            return Optional.empty();
        } else {
            return Optional.of(sponsors.get(index - 1));
        }
    }

}
