package com.nsa.charitystarter.service.charity;

import com.nsa.charitystarter.domain.Charity;

import java.util.List;
import java.util.Optional;


public interface CharityFinder {
    public Optional<Charity> findCharityByIndex(Integer index);

    public List<Charity> findCharityBySearch(String searchTerm);
}
